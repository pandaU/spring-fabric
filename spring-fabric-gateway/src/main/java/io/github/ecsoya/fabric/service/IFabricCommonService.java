package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.IFabricObject;

/**
 * <p>
 * The interface Fabric common service.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public interface IFabricCommonService<T extends IFabricObject> extends IFabricBlockCommonService<T> {

    /**
     * Create object and send to ledger.
     *
     * @param object the object
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricResponse create(T object);

    /**
     * Update give object at ledger.
     *
     * @param object the object
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricResponse update(T object);

    /**
     * Delete the object with given key from ledger.
     *
     * @param key the key
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricResponse delete(String key);

    /**
     * Get the object with key from ledger.
     *
     * @param key the key
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<T> get(String key);

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<List<T>> query(FabricQuery query);

    /**
     * Query objects from ledger with pagination, the query will be build to JSON
     * selector for CouchDB.
     *
     * @param query the query
     * @return the fabric pagination
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricPagination<T> pagination(FabricPaginationQuery<T> query);

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<Number> count(FabricQuery query);

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<Boolean> exists(FabricQuery query);

    /**
     * List all objects from ledger, this method should never be used.
     *
     * @return the fabric query response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public FabricQueryResponse<List<T>> list();

}
