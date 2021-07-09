/*
 * Copyright IBM Corp. All Rights Reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package application.java;

import java.nio.file.Paths;
import java.util.Properties;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class Enrollorg1admin {

	public static void main(String[] args) throws Exception {

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("ca.org1.xxzx.com-cert.pem").getPath().substring(1);
		props.put("pemFile",
			path);
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://ca.org1.xxzx.com:7054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the org1admin user.
		if (wallet.get("org1admin") != null) {
			System.out.println("An identity for the org1admin user \"org1admin\" already exists in the wallet");
			return;
		}

		// Enroll the org1admin user, and import the new identity into the wallet.
		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		enrollmentRequestTLS.addHost("ca.org1.example.com");
		enrollmentRequestTLS.setProfile("tls");
		Enrollment enrollment = caClient.enroll("org1admin", "org1adminpw", enrollmentRequestTLS);
		Identity user = Identities.newX509Identity("Org1MSP", enrollment);
		wallet.put("org1admin", user);
		System.out.println("Successfully enrolled user \"org1admin\" and imported it into the wallet");
	}
}
