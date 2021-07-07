package io.github.ecsoya.fabric.service;

import java.util.List;

import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.bean.IFabricObject;

/**
 * <p>
 * The interface Fabric service.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public interface IFabricService<T extends IFabricObject> extends IFabricBlockService<T> {

    /**
     * Create object and send to ledger.
     *
     * @param object the object
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public int extCreate(T object);

    /**
     * Update give object at ledger.
     *
     * @param object the object
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public int extUpdate(T object);

    /**
     * Delete the object with given key from ledger.
     *
     * @param key the key
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public int extDelete(String key);

    /**
     * Get the object with key from ledger.
     *
     * @param key the key
     * @return the t
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public T extGet(String key);

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public List<T> extQuery(FabricQuery query);

    /**
     * List all objects from ledger, this method should never be used.
     *
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public List<T> extList();

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the int
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:19
     */
    public int extCount(FabricQuery query);

    /**
     * Query objects from ledger, the query will be build to JSON selector for
     * CouchDB.
     *
     * @param query the query
     * @return the boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public boolean extExists(FabricQuery query);
}
