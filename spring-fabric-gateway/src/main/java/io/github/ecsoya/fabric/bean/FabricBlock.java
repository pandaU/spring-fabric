package io.github.ecsoya.fabric.bean;

import org.hyperledger.fabric.sdk.BlockInfo;

import com.google.protobuf.InvalidProtocolBufferException;

import io.github.ecsoya.fabric.utils.FabricUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Fabric block which contains blockNumber, dataHash...
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FabricBlock extends FabricIdentity {

	/**
	 * Channel
	 */
	private String channel;

	/**
	 * Transaction count
	 */
	private int transactionCount;

	/**
	 * Query context transaction id, @see {@link FabricHistory}
	 */
	private String currentTxId;

	/**
	 * Query context transaction timestamp, @see {@link FabricHistory}
	 */
	private String currentTxTimestamp;

	/**
	 * Current hash
	 */
	private String currentHash;
	/**
	 * Previous hash
	 */
	private String previousHash;

	/**
	 * Create fabric block.
	 *
	 * @param blockInfo the block info
	 * @return the fabric block
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:14
	 */
	public static FabricBlock create(BlockInfo blockInfo) {
		if (blockInfo == null) {
			return null;
		}
		FabricBlock block = new FabricBlock();
		block.setBlockNumber(blockInfo.getBlockNumber());
		try {
			block.setChannel(blockInfo.getChannelId());
		} catch (InvalidProtocolBufferException e) {
		}
		block.setCurrentHash(FabricUtil.hashToString(blockInfo.getDataHash()));
		block.setDataHash(FabricUtil.hashToString(blockInfo.getDataHash()));
		block.setTransactionCount(blockInfo.getTransactionCount());

		block.setPreviousHash(FabricUtil.hashToString(blockInfo.getPreviousHash()));

		return block;
	}
}
