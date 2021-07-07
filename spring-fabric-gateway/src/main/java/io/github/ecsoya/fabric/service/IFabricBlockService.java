package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.IFabricObject;

/**
 * <p>
 * The interface Fabric block service.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public interface IFabricBlockService<T extends IFabricObject> {

    /**
     * Ext history list.
     *
     * @param key the key
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    List<FabricHistory> extHistory(String key);

    /**
     * Ext block fabric block.
     *
     * @param key the key
     * @return the fabric block
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricBlock extBlock(String key);
}
