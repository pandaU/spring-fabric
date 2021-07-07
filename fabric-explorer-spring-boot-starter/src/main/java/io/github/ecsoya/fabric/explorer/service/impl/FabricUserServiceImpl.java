package io.github.ecsoya.fabric.explorer.service.impl;

import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.chaincode.FunctionType;
import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricUserObject;
import io.github.ecsoya.fabric.explorer.service.FabricUserService;
import io.github.ecsoya.fabric.service.impl.AbstractFabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * The type Fabric user service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Service
public class FabricUserServiceImpl extends AbstractFabricService<FabricUserObject> implements FabricUserService {


    /**
     * Fabric user service
     *
     * @param fabricContext fabric context
     */
    @Autowired
    public FabricUserServiceImpl(FabricContext fabricContext) {
        super(fabricContext);
    }
    @Override
    public int extDelete(String key, String type) {
        return delete(key, type).status;
    }

    @Override
    public FabricUserObject extGet(String key, String type) {
        return get(key, type).data;
    }

    @Override
    public FabricResponse delete(String key, String type) {
        if (key == null) {
            return FabricResponse.fail("Invalid argument: key");
        }
        if (type == null) {
            return FabricResponse.fail("Invalid argument: type");
        }
        return execute(newRequest(getFunction(FunctionType.FUNCTION_DELETE), type, key));
    }

    @Override
    public FabricQueryResponse<FabricUserObject> get(String key, String type) {
        if (key == null) {
            return FabricQueryResponse.failure("Invalid argument: key");
        }
        if (type == null) {
            return FabricQueryResponse.failure("Invalid argument: type");
        }
        return query(newQueryRequest(getGenericType(), getFunction(FunctionType.FUNCTION_GET), type, key));
    }

    @Override
    public FabricQueryResponse<List<FabricHistory>> history(String key, String type) {
        if (key == null) {
            return FabricQueryResponse.failure("Invalid argument: key");
        }
        if (type == null) {
            return FabricQueryResponse.failure("Invalid argument: type");
        }
        return queryMany(newQueryRequest(FabricHistory.class, getFunction(FunctionType.FUNCTION_HISTORY), type, key));
    }

    @Override
    public FabricQueryResponse<FabricBlock> block(String key, String type) {
        if (key == null) {
            return FabricQueryResponse.failure("Invalid argument: key");
        }
        if (type == null) {
            return FabricQueryResponse.failure("Invalid argument: type");
        }
        FabricQueryResponse<List<FabricHistory>> historyRes = history(key, type);
        if (historyRes.isOk(true)) {
            List<FabricHistory> histories = historyRes.data;
            long blockHeight = 0;
            FabricBlock result = null;
            for (FabricHistory fabricHistory : histories) {
                FabricBlock block = fabricHistory.getBlock();
                if (block == null) {
                    continue;
                }
                if (result == null) {
                    result = block;
                    blockHeight = block.getBlockNumber();
                } else if (block.getBlockNumber() < blockHeight) {
                    result = block;
                }
            }
            if (result != null) {
                return FabricQueryResponse.success(result);
            } else {
                return FabricQueryResponse.failure("No block found for key: " + key);
            }
        }
        return FabricQueryResponse
                .failure("Could not get block for key: " + key + " with error: " + historyRes.errorMsg);
    }

    @Override
    public List<FabricHistory> extHistory(String key, String type) {
        return history(key, type).data;
    }

    @Override
    public FabricBlock extBlock(String key, String type) {
        return block(key, type).data;
    }
}
