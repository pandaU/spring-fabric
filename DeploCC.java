package application.java;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

public class DeploCC {

    public static void deployCC(String chaincodeName,HFClient adminClient,Channel xxzx ,Long seq ,String version) throws InvalidArgumentException, ProposalException, IOException, InterruptedException, ExecutionException, TimeoutException {
        LifecycleInstallChaincodeRequest request = adminClient.newLifecycleInstallChaincodeRequest();
        String chainCodeResource = "C:\\Users\\MSI\\Desktop\\chaincode-java\\build\\install\\chaincode-java";
        LifecycleChaincodePackage test = LifecycleChaincodePackage.fromSource(chaincodeName, Paths.get(chainCodeResource), TransactionRequest.Type.JAVA, null, null);
        request.setLifecycleChaincodePackage(test);
        Collection<Peer> peers = xxzx.getPeers();
        Collection<LifecycleInstallChaincodeProposalResponse> responses = adminClient.sendLifecycleInstallChaincodeRequest(request,peers);
        String packageId =null ;
        for (LifecycleInstallChaincodeProposalResponse x: responses) {
            packageId  = x.getPackageId();
        }
        LifecycleApproveChaincodeDefinitionForMyOrgRequest orgRequest = adminClient.newLifecycleApproveChaincodeDefinitionForMyOrgRequest();
        orgRequest.setChaincodeName(chaincodeName);
        orgRequest.setPackageId(packageId);
        orgRequest.setChaincodeVersion(version);
        orgRequest.setSequence(seq);
        Collection<LifecycleApproveChaincodeDefinitionForMyOrgProposalResponse> myOrgProposalResponses = xxzx.sendLifecycleApproveChaincodeDefinitionForMyOrgProposal(orgRequest, peers);
        //确认approve
        CompletableFuture<BlockEvent.TransactionEvent> future = xxzx.sendTransaction(myOrgProposalResponses);
        //5秒ttl得到异步返回值
        future.get(5, TimeUnit.SECONDS);
        LifecycleCheckCommitReadinessRequest commitReadinessRequest = adminClient.newLifecycleSimulateCommitChaincodeDefinitionRequest();
        commitReadinessRequest.setChaincodeName(chaincodeName);
        commitReadinessRequest.setChaincodeVersion(version);
        commitReadinessRequest.setSequence(seq);
        Collection<LifecycleCheckCommitReadinessProposalResponse> proposalResponses = xxzx.sendLifecycleCheckCommitReadinessRequest(commitReadinessRequest, peers);
        proposalResponses.forEach(x->{
            try {
                Map<String, Boolean> map = x.getApprovalsMap();
                System.out.println(map);
            } catch (ProposalException e) {
                e.printStackTrace();
            }
        });
        LifecycleCommitChaincodeDefinitionRequest request1 = adminClient.newLifecycleCommitChaincodeDefinitionRequest();
        request1.setChaincodeName(chaincodeName);
        request1.setChaincodeVersion(version);
        request1.setSequence(seq);
        Collection<LifecycleCommitChaincodeDefinitionProposalResponse> responses1 = xxzx.sendLifecycleCommitChaincodeDefinitionProposal(request1,peers);
        //确认commit
        xxzx.sendTransaction(responses1);
    }
}
