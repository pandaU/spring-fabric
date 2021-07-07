package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricQueryRequest;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.FabricRequest;
import io.github.ecsoya.fabric.FabricResponse;

/**
 * <p>
 * The interface Fabric base service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public interface IFabricBaseService {

    /**
     * Execute fabric response.
     *
     * @param request the request
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    FabricResponse execute(FabricRequest request);

    /**
     * Query fabric query response.
     *
     * @param <T>     the type parameter
     * @param request the request
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public <T> FabricQueryResponse<T> query(FabricQueryRequest<T> request);

    /**
     * Query many fabric query response.
     *
     * @param <T>     the type parameter
     * @param request the request
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public <T> FabricQueryResponse<List<T>> queryMany(FabricQueryRequest<T> request);

}
