package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.FabricLedger;
import io.github.ecsoya.fabric.bean.FabricTransaction;

/**
 * Default service to provided fabric blockchain info, such as blocks,
 * transactions and ledger.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
public interface IFabricInfoService {

    /**
     * Query Fabric Info.
     *
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<FabricLedger> queryFabricLedger();

    /**
     * Query fabric block by using block number.
     *
     * @param blockNumber the block number
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<FabricBlock> queryBlockByNumber(long blockNumber);

    /**
     * Query fabric block by using transaction id.
     *
     * @param txId the tx id
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<FabricBlock> queryBlockByTransactionId(String txId);

    /**
     * Query fabric block by using block hash.
     *
     * @param blockHash the block hash
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<FabricBlock> queryBlockByHash(byte[] blockHash);

    /**
     * Paging query fabric blocks.
     *
     * @param query the query
     * @return the fabric pagination
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricPagination<FabricBlock> queryBlocks(FabricPaginationQuery<FabricBlock> query);

    /**
     * Query all transactions of a block number.
     *
     * @param blockNumber the block number
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<List<FabricTransaction>> queryTransactions(long blockNumber);

    /**
     * Query transaction reads and writes of a transaction id.
     *
     * @param txId the tx id
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<String> queryTransactionRwSet(String txId);

    /**
     * Query history of object with given key and type.
     *
     * @param type the type
     * @param key  the key
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<List<FabricHistory>> queryHistory(String type, String key);

    /**
     * Query transaction with id.
     *
     * @param txid the txid
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricQueryResponse<FabricTransaction> queryTransaction(String txid);
}
