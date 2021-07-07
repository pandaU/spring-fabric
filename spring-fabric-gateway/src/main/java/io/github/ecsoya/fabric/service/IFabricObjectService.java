package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.FabricObject;

/**
 * Default service which holding the bean {@link FabricObject}
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
public interface IFabricObjectService
		extends IFabricBaseService, IFabricCommonService<FabricObject>, IFabricService<FabricObject> {
    /**
     * Delete the object with given key from ledger.
     *
     * @param key  the key
     * @param type the type
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public int extDelete(String key, String type);

    /**
     * Get the object with key from ledger.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric object
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricObject extGet(String key, String type);

    /**
     * Delete the object with given key from ledger.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricResponse delete(String key, String type);

    /**
     * Get the object with key from ledger.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<FabricObject> get(String key, String type);

    /**
     * Get the history of object with key from ledger.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<List<FabricHistory>> history(String key, String type);

    /**
     * Query the block info
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<FabricBlock> block(String key, String type);

    /**
     * Ext history list.
     *
     * @param key  the key
     * @param type the type
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    List<FabricHistory> extHistory(String key, String type);

    /**
     * Ext block fabric block.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric block
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricBlock extBlock(String key, String type);
}
