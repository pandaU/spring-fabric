package io.github.ecsoya.fabric.service;

import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * The interface Chain code service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-12
 */
public interface ChainCodeService {

    /**
     * Deploy chain code.
     *
     * @param chainCodeName the chain code name
     * @param version       the version
     * @param policy        the policy
     * @param path          the path
     * @author XieXiongXiong
     * @date 2021 -07-12 15:54:32
     */
    void deployChainCode(String chainCodeName, String version, String policy, String path, Long seq) throws InvalidArgumentException, ProposalException, IOException, InterruptedException, ExecutionException, TimeoutException, ChaincodeEndorsementPolicyParseException;

    /**
     * Update version.
     *
     * @param chainCodeId the chain code id
     * @param version     the version
     * @author XieXiongXiong
     * @date 2021 -07-12 15:54:32
     */
    void updateVersion(String chainCodeId ,String version);
}
