package io.github.ecsoya.fabric.explorer.service.impl;

import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.explorer.repository.dao.ChainCodeDAO;
import io.github.ecsoya.fabric.explorer.repository.entity.ChainCodeEntity;
import io.github.ecsoya.fabric.explorer.service.ChainCodeService;
import io.github.ecsoya.fabric.utils.HfClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * The type Chaincode service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-12
 */
@Slf4j
public class ChainCodeServiceImpl implements ChainCodeService {
    @Autowired
    private ChainCodeDAO chainCodeDAO;

    private Network network;

    public ChainCodeServiceImpl(FabricContext fabricContext) {
        this.network = fabricContext.getNetwork();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployChainCode(String chainCodeName, String version, String policy, String path, Long seq, String language) throws InvalidArgumentException, ProposalException, IOException, InterruptedException, ExecutionException, TimeoutException, ChaincodeEndorsementPolicyParseException {
        Channel channel = network.getChannel();
        LifecycleChaincodeEndorsementPolicy endorsementPolicy = null;
        if (policy != null && !policy.isEmpty()) {
            endorsementPolicy = LifecycleChaincodeEndorsementPolicy.fromPolicyReference(policy);
        }
        if (seq == null) {
            seq = 1L;
        }
        LifecycleInstallChaincodeRequest request = HfClientUtil.CLIENT.newLifecycleInstallChaincodeRequest();
        LifecycleChaincodePackage codePackage = LifecycleChaincodePackage.fromSource(chainCodeName, Paths.get(path), TransactionRequest.Type.JAVA, null, null);
        request.setLifecycleChaincodePackage(codePackage);
        Collection<Peer> peers = channel.getPeers();
        Collection<LifecycleInstallChaincodeProposalResponse> responses = HfClientUtil.CLIENT.sendLifecycleInstallChaincodeRequest(request, peers);
        String packageId = null;
        for (LifecycleInstallChaincodeProposalResponse x : responses) {
            packageId = x.getPackageId();
        }
        LifecycleApproveChaincodeDefinitionForMyOrgRequest orgRequest = HfClientUtil.CLIENT.newLifecycleApproveChaincodeDefinitionForMyOrgRequest();
        orgRequest.setChaincodeName(chainCodeName);
        orgRequest.setPackageId(packageId);
        orgRequest.setChaincodeVersion(version);
        orgRequest.setSequence(seq);
        if (endorsementPolicy != null) {
            orgRequest.setChaincodeEndorsementPolicy(endorsementPolicy);
        }
        Collection<LifecycleApproveChaincodeDefinitionForMyOrgProposalResponse> myOrgProposalResponses = channel.sendLifecycleApproveChaincodeDefinitionForMyOrgProposal(orgRequest, peers);
        //确认approve
        CompletableFuture<BlockEvent.TransactionEvent> future = channel.sendTransaction(myOrgProposalResponses);
        //5秒ttl得到异步返回值
        future.get(10, TimeUnit.SECONDS);
        LifecycleCheckCommitReadinessRequest commitReadinessRequest = HfClientUtil.CLIENT.newLifecycleSimulateCommitChaincodeDefinitionRequest();
        commitReadinessRequest.setChaincodeName(chainCodeName);
        commitReadinessRequest.setChaincodeVersion(version);
        commitReadinessRequest.setSequence(seq);
        if (endorsementPolicy != null) {
            commitReadinessRequest.setChaincodeEndorsementPolicy(endorsementPolicy);
        }
        Collection<LifecycleCheckCommitReadinessProposalResponse> proposalResponses = channel.sendLifecycleCheckCommitReadinessRequest(commitReadinessRequest, peers);
        proposalResponses.forEach(x -> {
            try {
                Map<String, Boolean> map = x.getApprovalsMap();
                log.info("组织审批意见{}", map);
            } catch (ProposalException e) {
                e.printStackTrace();
            }
        });
        LifecycleCommitChaincodeDefinitionRequest request1 = HfClientUtil.CLIENT.newLifecycleCommitChaincodeDefinitionRequest();
        request1.setChaincodeName(chainCodeName);
        request1.setChaincodeVersion(version);
        request1.setSequence(seq);
        if (endorsementPolicy != null) {
            request1.setChaincodeEndorsementPolicy(endorsementPolicy);
        }
        Collection<LifecycleCommitChaincodeDefinitionProposalResponse> responses1 = channel.sendLifecycleCommitChaincodeDefinitionProposal(request1, peers);
        //确认commit
        //下架该合约正在运行的版本
        chainCodeDAO.offChainCode(chainCodeName);
        channel.sendTransaction(responses1);
        ChainCodeEntity entity = new ChainCodeEntity();
        entity.setChainCodeName(chainCodeName);
        entity.setChainCodeVersion(version);
        entity.setChainCodeSequence(seq);
        entity.setChainCodePolicy(policy);
        entity.setChainCodeLanguage(language);
        entity.setChainCodePackageId(packageId);
        entity.setStatus(1);
        chainCodeDAO.insertChainCode(entity);
    }

    @Override
    public void updateVersion(String chainCodeId, String version) {

    }

    @Override
    public  List<ChainCodeEntity> getChainCode(String chainCodeName, Integer currentPage, Integer pageSize){
        List<ChainCodeEntity> entities = chainCodeDAO.listChainCode(chainCodeName,currentPage,pageSize);
        return entities;
    }

    @Override
    public List<ChainCodeEntity> getHistory(String chainCodeName, Integer currentPage, Integer pageSize) {
        List<ChainCodeEntity> entities = chainCodeDAO.listChainCodeVersion(chainCodeName,currentPage,pageSize);
        return entities;
    }


    @Override
    public Long getChainCodeNextSeq(String chainCodeName){
        return chainCodeDAO.nextSeq(chainCodeName);
    }
}
