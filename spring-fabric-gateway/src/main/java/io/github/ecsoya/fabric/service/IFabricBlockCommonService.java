package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.IFabricObject;

/**
 * <p>
 * The interface Fabric block common service.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public interface IFabricBlockCommonService<T extends IFabricObject> {

    /**
     * Get the history of object with key from ledger.
     *
     * @param key the key
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<List<FabricHistory>> history(String key);

    /**
     * Query the block info
     *
     * @param key the key
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<FabricBlock> block(String key);

}
