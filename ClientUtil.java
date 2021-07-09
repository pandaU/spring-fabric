package application.java;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.security.PrivateKey;
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
public class ClientUtil {

    /**
     * CLIENT
     */
    public static HFClient CLIENT;

    static {
        try {
            CLIENT = getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private static HFClient getClient() throws InvalidArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CryptoException, IOException {
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
        X509Identity adminIdentity = (X509Identity) wallet.get("org1admin");
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
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuiteFactory.getDefault().getCryptoSuite());
        client.setUserContext(admin);
        client.setExecutorService(new ThreadPoolExecutor(8, 8, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(99)));
        return client;
    }
}
