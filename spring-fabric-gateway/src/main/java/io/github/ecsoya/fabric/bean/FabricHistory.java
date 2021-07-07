package io.github.ecsoya.fabric.bean;

import org.hyperledger.fabric.sdk.BlockInfo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Editing history for a key.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
public class FabricHistory {

    /**
     * Transaction id of current history.
     */
    private String txId;


    /**
     * Modify timestamp of current history.
     */
    private String timestamp;

    /**
     * Mark is deleted or not.
     */
    private boolean isDelete;

    /**
     * Block object.
     */
    private FabricBlock block;

    /**
     * Value
     */
    private Map<String, Object> value;

    /**
     * Sets block info.
     *
     * @param blockInfo the block info
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    public void setBlockInfo(BlockInfo blockInfo) {
		if (blockInfo == null) {
			return;
		}
		block = FabricBlock.create(blockInfo);
		block.setCurrentTxId(txId);
		block.setCurrentTxTimestamp(timestamp);
	}

//	public <T> T extract(Class<T> type) {
//		if (value == null || type == null) {
//			return null;
//		}
//		Gson gson = new Gson();
//		return gson.fromJson(value, type);
//	}
}
