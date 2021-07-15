package io.github.ecsoya.fabric.explorer.service;

import io.github.ecsoya.fabric.explorer.repository.entity.ChainCodeEntity;
import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.io.IOException;
import java.util.List;
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
     * @param seq           the seq
     * @param language      the language
     * @throws InvalidArgumentException                 the invalid argument exception
     * @throws ProposalException                        the proposal exception
     * @throws IOException                              the io exception
     * @throws InterruptedException                     the interrupted exception
     * @throws ExecutionException                       the execution exception
     * @throws TimeoutException                         the timeout exception
     * @throws ChaincodeEndorsementPolicyParseException the chaincode endorsement policy parse exception
     * @author XieXiongXiong
     * @date 2021 -07-12 15:54:32
     */
    void deployChainCode(String chainCodeName, String version, String policy, String path, Long seq ,String language) throws InvalidArgumentException, ProposalException, IOException, InterruptedException, ExecutionException, TimeoutException, ChaincodeEndorsementPolicyParseException;

    /**
     * Update version.
     *
     * @param chainCodeId the chain code id
     * @param version     the version
     * @author XieXiongXiong
     * @date 2021 -07-12 15:54:32
     */
    void updateVersion(String chainCodeId ,String version);

    /**
     * Gets chain code.
     *
     * @param chainCodeName the chain code name
     * @param currentPage   the current page
     * @param pageSize      the page size
     * @return the chain code
     * @throws InvalidArgumentException the invalid argument exception
     * @throws ProposalException        the proposal exception
     * @author XieXiongXiong
     * @date 2021 -07-15 10:56:36
     */
    List<ChainCodeEntity> getChainCode(String chainCodeName, Integer currentPage, Integer pageSize);

    /**
     * Gets history.
     *
     * @param chainCodeName the chain code name
     * @param currentPage   the current page
     * @param pageSize      the page size
     * @return the history
     * @author XieXiongXiong
     * @date 2021 -07-15 17:07:55
     */
    List<ChainCodeEntity> getHistory(String chainCodeName, Integer currentPage, Integer pageSize);

    /**
     * Gets chain code next seq.
     *
     * @param chainCodeName the chain code name
     * @return the chain code next seq
     * @author XieXiongXiong
     * @date 2021 -07-15 11:15:07
     */
    Long getChainCodeNextSeq(String chainCodeName);
}
