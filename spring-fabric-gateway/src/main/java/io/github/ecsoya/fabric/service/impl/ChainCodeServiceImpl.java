package io.github.ecsoya.fabric.service.impl;

import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.service.ChainCodeService;
import io.github.ecsoya.fabric.utils.HfClientUtil;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
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
public class ChainCodeServiceImpl implements ChainCodeService {

    private Network network;

    public ChainCodeServiceImpl(FabricContext fabricContext) {
        this.network = fabricContext.getNetwork();
    }

    @Override
    public void deployChainCode(String chainCodeName, String version, String policy, String path, Long seq) throws InvalidArgumentException, ProposalException, IOException, InterruptedException, ExecutionException, TimeoutException, ChaincodeEndorsementPolicyParseException {
        Channel channel = network.getChannel();
        LifecycleChaincodeEndorsementPolicy endorsementPolicy= null;
        if (policy != null && !policy.isEmpty()){
            endorsementPolicy = LifecycleChaincodeEndorsementPolicy.fromPolicyReference(policy);
        }
        if (seq == null){
            seq = 1L;
        }
        LifecycleInstallChaincodeRequest request = HfClientUtil.CLIENT.newLifecycleInstallChaincodeRequest();
        LifecycleChaincodePackage test = LifecycleChaincodePackage.fromSource(chainCodeName, Paths.get(path), TransactionRequest.Type.JAVA, null, null);
        request.setLifecycleChaincodePackage(test);
        Collection<Peer> peers = channel.getPeers();
        Collection<LifecycleInstallChaincodeProposalResponse> responses = HfClientUtil.CLIENT.sendLifecycleInstallChaincodeRequest(request,peers);
        String packageId =null;
        for (LifecycleInstallChaincodeProposalResponse x: responses) {
            packageId  = x.getPackageId();
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
        future.get(5, TimeUnit.SECONDS);
        LifecycleCheckCommitReadinessRequest commitReadinessRequest = HfClientUtil.CLIENT.newLifecycleSimulateCommitChaincodeDefinitionRequest();
        commitReadinessRequest.setChaincodeName(chainCodeName);
        commitReadinessRequest.setChaincodeVersion(version);
        commitReadinessRequest.setSequence(seq);
        if (endorsementPolicy != null) {
            commitReadinessRequest.setChaincodeEndorsementPolicy(endorsementPolicy);
        }
        Collection<LifecycleCheckCommitReadinessProposalResponse> proposalResponses = channel.sendLifecycleCheckCommitReadinessRequest(commitReadinessRequest, peers);
        proposalResponses.forEach(x->{
            try {
                Map<String, Boolean> map = x.getApprovalsMap();
                System.out.println(map);
            } catch (ProposalException e) {
                e.printStackTrace();
            }
        });
        LifecycleCommitChaincodeDefinitionRequest request1 = HfClientUtil.CLIENT.newLifecycleCommitChaincodeDefinitionRequest();
        request1.setChaincodeName(chainCodeName);
        request1.setChaincodeVersion(version);
        request1.setSequence(seq);
        if (endorsementPolicy != null){
            request1.setChaincodeEndorsementPolicy(endorsementPolicy);
        }
        Collection<LifecycleCommitChaincodeDefinitionProposalResponse> responses1 = channel.sendLifecycleCommitChaincodeDefinitionProposal(request1,peers);
        //确认commit
        channel.sendTransaction(responses1);
    }

    @Override
    public void updateVersion(String chainCodeId, String version) {

    }
}
