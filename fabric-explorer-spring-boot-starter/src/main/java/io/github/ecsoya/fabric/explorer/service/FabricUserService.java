package io.github.ecsoya.fabric.explorer.service;

import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricUserObject;
import io.github.ecsoya.fabric.service.IFabricBaseService;
import io.github.ecsoya.fabric.service.IFabricCommonService;
import io.github.ecsoya.fabric.service.IFabricService;

import java.util.List;

/**
 * <p>
 * The interface Fabric user service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public interface FabricUserService
        extends IFabricBaseService, IFabricCommonService<FabricUserObject>, IFabricService<FabricUserObject> {
    /**
     * Ext delete int.
     *
     * @param key  the key
     * @param type the type
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public int extDelete(String key, String type);

    /**
     * Ext get fabric user object.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric user object
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public FabricUserObject extGet(String key, String type);

    /**
     * Delete fabric response.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public FabricResponse delete(String key, String type);

    /**
     * Get fabric query response.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public FabricQueryResponse<FabricUserObject> get(String key, String type);

    /**
     * History fabric query response.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public FabricQueryResponse<List<FabricHistory>> history(String key, String type);

    /**
     * Block fabric query response.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public FabricQueryResponse<FabricBlock> block(String key, String type);

    /**
     * Ext history list.
     *
     * @param key  the key
     * @param type the type
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    List<FabricHistory> extHistory(String key, String type);

    /**
     * Ext block fabric block.
     *
     * @param key  the key
     * @param type the type
     * @return the fabric block
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    FabricBlock extBlock(String key, String type);
}
