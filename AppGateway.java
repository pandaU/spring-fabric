package application.java;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AppGateway {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
    }
    public static void main(String[] args) throws Exception {
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
        X509Identity adminIdentity = (X509Identity)wallet.get("org1admin");
        User admin = new User() {

            @Override
            public String getName() {
                return "org1admin";
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return null;
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

                    @Override
                    public PrivateKey getKey() {
                        return adminIdentity.getPrivateKey();
                    }

                    @Override
                    public String getCert() {
                        return Identities.toPemString(adminIdentity.getCertificate());
                    }
                };
            }

            @Override
            public String getMspId() {
                return "Org1MSP";
            }

        };
        String chaincodeName = "common-test";
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuiteFactory.getDefault().getCryptoSuite());
        client.setUserContext(admin);
        Gateway gateway = connect();
        Channel xxzx = gateway.getNetwork("xxzx").getChannel();
        LifecycleInstallChaincodeRequest request = client.newLifecycleInstallChaincodeRequest();
        String chainCodeResource = "C:\\Users\\MSI\\Desktop\\chaincode-java\\build\\install\\chaincode-java";
        LifecycleChaincodePackage test = LifecycleChaincodePackage.fromSource(chaincodeName, Paths.get(chainCodeResource), TransactionRequest.Type.JAVA, null, null);
        request.setLifecycleChaincodePackage(test);
        Collection<Peer> peers = xxzx.getPeers();
        Collection<LifecycleInstallChaincodeProposalResponse> responses = client.sendLifecycleInstallChaincodeRequest(request,peers);
        String packageId =null ;
        for (LifecycleInstallChaincodeProposalResponse x: responses) {
            packageId  = x.getPackageId();
        }
        LifecycleApproveChaincodeDefinitionForMyOrgRequest orgRequest = client.newLifecycleApproveChaincodeDefinitionForMyOrgRequest();
        orgRequest.setChaincodeName(chaincodeName);
        orgRequest.setPackageId(packageId);
        orgRequest.setChaincodeVersion("1");
        orgRequest.setSequence(1);
        Collection<LifecycleApproveChaincodeDefinitionForMyOrgProposalResponse> myOrgProposalResponses = xxzx.sendLifecycleApproveChaincodeDefinitionForMyOrgProposal(orgRequest, peers);
        //确认approve
        CompletableFuture<BlockEvent.TransactionEvent> future = xxzx.sendTransaction(myOrgProposalResponses);
        //5秒ttl得到异步返回值
        future.get(5, TimeUnit.SECONDS);
        LifecycleCheckCommitReadinessRequest commitReadinessRequest = client.newLifecycleSimulateCommitChaincodeDefinitionRequest();
        commitReadinessRequest.setChaincodeName(chaincodeName);
        commitReadinessRequest.setChaincodeVersion("1");
        commitReadinessRequest.setSequence(1);
        Collection<LifecycleCheckCommitReadinessProposalResponse> proposalResponses = xxzx.sendLifecycleCheckCommitReadinessRequest(commitReadinessRequest, peers);
        proposalResponses.forEach(x->{
            try {
                Map<String, Boolean> map = x.getApprovalsMap();
                System.out.println(map);
            } catch (ProposalException e) {
                e.printStackTrace();
            }
        });
        LifecycleCommitChaincodeDefinitionRequest request1 = client.newLifecycleCommitChaincodeDefinitionRequest();
        request1.setChaincodeName(chaincodeName);
        request1.setChaincodeVersion("1");
        request1.setSequence(1);
        Collection<LifecycleCommitChaincodeDefinitionProposalResponse> responses1 = xxzx.sendLifecycleCommitChaincodeDefinitionProposal(request1,peers);
        //确认commit
        xxzx.sendTransaction(responses1);
    }

    public static Gateway connect() throws Exception{
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        String path = Thread.currentThread().getContextClassLoader().getResource("connection-org1.yaml").getPath().substring(1);
        Path networkConfigPath = Paths.get(path);

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1admin").networkConfig(networkConfigPath).discovery(true);
        return builder.connect();
    }
}
