package io.github.ecsoya.fabric.utils;

import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.config.FabricWalletProperties;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * The type Client util.
 *
 * @author XieXiongXiong
 * @date 2021 -07-09
 */
public class HfClientUtil {

    /**
     * Fabric context
     */
    private FabricContext fabricContext;
    /**
     * CLIENT
     */
    public static HFClient CLIENT;


    /**
     * Hf client util
     *
     * @param fabricContext fabric context
     */
    public HfClientUtil(FabricContext fabricContext) {
        this.fabricContext = fabricContext;
    }

    /**
     * Gets client.
     *
     * @return the client
     * @throws InvalidArgumentException  the invalid argument exception
     * @throws ClassNotFoundException    the class not found exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws CryptoException           the crypto exception
     * @throws IOException               the io exception
     * @author XieXiongXiong
     * @date 2021 -07-09 09:33:16
     */
    @PostConstruct
    private  void getClient() throws InvalidArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CryptoException, IOException {
        Wallet wallet = fabricContext.getWallet();
        FabricWalletProperties walletProperties = fabricContext.getProperties().getGateway().getWallet();
        X509Identity adminIdentity = (X509Identity) wallet.get(walletProperties.getIdentity());
        User admin = new User() {

            @Override
            public String getName() {
                return walletProperties.getIdentity();
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
                return walletProperties.getCaMsp();
            }

        };
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuiteFactory.getDefault().getCryptoSuite());
        client.setUserContext(admin);
        CLIENT = client;
    }

    /**
     * Gets orderers.
     *
     * @return the orderers
     * @throws InvalidArgumentException the invalid argument exception
     * @author XieXiongXiong
     * @date 2021 -07-12 11:42:52
     */
    public static Collection<Orderer> getOrderers() throws InvalidArgumentException {
        Properties orderer1Prop = new Properties();
        String orderPath = Thread.currentThread().getContextClassLoader().getResource("localhost-9054-ca-orderer.pem").getPath().substring(1);
        orderer1Prop.setProperty("pemFile", orderPath);
        orderer1Prop.setProperty("sslProvider", "openSSL");
        orderer1Prop.setProperty("negotiationType", "TLS");
        orderer1Prop.setProperty("hostnameOverride", "orderer.xxzx.com");
        orderer1Prop.setProperty("trustServerCertificate", "true");
        orderer1Prop.put("grpc.NettyChannelBuilderOption.maxInboundMessageSize", 9000000);
        Orderer orderer = CLIENT.newOrderer("orderer.xxzx.com", "grpcs://orderer.xxzx.com:7050", orderer1Prop);
        ArrayList<Orderer> list = new ArrayList<>();
        list.add(orderer);
        return list;
    }

    /**
     * Gets peers.
     *
     * @return the peers
     * @throws InvalidArgumentException the invalid argument exception
     * @author XieXiongXiong
     * @date 2021 -07-12 11:42:52
     */
    public static Collection<Peer> getPeers() throws InvalidArgumentException {
        String peerPath = Thread.currentThread().getContextClassLoader().getResource("ca.org1.xxzx.com-cert.pem").getPath().substring(1);
        Properties peer1Prop = new Properties();
        peer1Prop.setProperty("pemFile", peerPath);
        peer1Prop.setProperty("sslProvider", "openSSL");
        peer1Prop.setProperty("negotiationType", "TLS");
        peer1Prop.setProperty("hostnameOverride", "peer0.org1.xxzx.com");
        peer1Prop.setProperty("trustServerCertificate", "true");
        peer1Prop.put("grpc.NettyChannelBuilderOption.maxInboundMessageSize", 9000000);
        Peer peer = CLIENT.newPeer("peer0.org1.xxzx.com", "grpcs://peer0.org1.xxzx.com:8051", peer1Prop);
        ArrayList<Peer> list = new ArrayList<>();
        list.add(peer);
        return list;
    }
}
