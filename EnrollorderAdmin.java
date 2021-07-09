package application.java;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Set;

public class EnrollorderAdmin {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CryptoException, InvalidArgumentException, EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, CertificateException {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        String path = Thread.currentThread().getContextClassLoader().getResource("localhost-9054-ca-orderer.pem").getPath().substring(1);
        props.put("pemFile",
                path);
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance("https://orderer.xxzx.com:9054", props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the org1admin user.
        if (wallet.get("ordererAdmin") != null) {
            System.out.println("An identity for the org1admin user \"org1admin\" already exists in the wallet");
            return;
        }

        // Enroll the org1admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("orderer.xxzx.com");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("ordererAdmin", "ordererAdminpw", enrollmentRequestTLS);
        Identity user = Identities.newX509Identity("OrdererMSP", enrollment);
        wallet.put("ordererAdmin", user);
        System.out.println("Successfully enrolled user \"org1admin\" and imported it into the wallet");
    }
    public static User   getOrdererAdmin() throws IOException {
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
        X509Identity adminIdentity = (X509Identity)wallet.get("ordererAdmin");
        User admin = new User() {

            @Override
            public String getName() {
                return "ordererAdmin";
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
                return "OrdererMSP";
            }

        };
        return admin;
    }
}
