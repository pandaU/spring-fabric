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

public interface FabricUserService
        extends IFabricBaseService, IFabricCommonService<FabricUserObject>, IFabricService<FabricUserObject> {
    public int extDelete(String key, String type);

    /**
     *
     * Get the object with key from ledger.
     *
     */
    public FabricUserObject extGet(String key, String type);

    /**
     * Delete the object with given key from ledger.
     *
     */
    public FabricResponse delete(String key, String type);

    /**
     *
     * Get the object with key from ledger.
     *
     *
     */
    public FabricQueryResponse<FabricUserObject> get(String key, String type);

    /**
     *
     * Get the history of object with key from ledger.
     *
     *
     */
    public FabricQueryResponse<List<FabricHistory>> history(String key, String type);

    /**
     *
     * Query the block info
     *
     */
    public FabricQueryResponse<FabricBlock> block(String key, String type);

    List<FabricHistory> extHistory(String key, String type);

    FabricBlock extBlock(String key, String type);
}
