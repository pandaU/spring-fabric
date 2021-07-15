package io.github.ecsoya.fabric.explorer.repository.dao;

import io.github.ecsoya.fabric.explorer.repository.entity.ChainCodeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * The interface Chain code dao.
 *
 * @author XieXiongXiong
 * @date 2021 -07-15
 */
public interface ChainCodeDAO {

    /**
     * List chain code list.
     *
     * @param chainCodeName the name
     * @param currentPage   the current page
     * @param pageSize      the page size
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-15 10:45:14
     */
    List<ChainCodeEntity> listChainCode(String chainCodeName, Integer currentPage, Integer pageSize);

    /**
     * Insert chain code.
     *
     * @param entity the entity
     * @author XieXiongXiong
     * @date 2021 -07-15 10:47:10
     */
    void insertChainCode(ChainCodeEntity entity);

    /**
     * List chain code verison list.
     *
     * @param chainCodeName the chain code name
     * @param currentPage   the current page
     * @param pageSize      the page size
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-15 17:08:40
     */
    List<ChainCodeEntity> listChainCodeVersion(String chainCodeName, Integer currentPage, Integer pageSize);

    /**
     * Max seq long.
     *
     * @param chainCodeName the String
     * @return the long
     * @author XieXiongXiong
     * @date 2021 -07-15 11:06:49
     */
    Long nextSeq(String chainCodeName);


    /**
     * Off chain code.
     *
     * @param chainCodeName the chain code name
     * @author XieXiongXiong
     * @date 2021 -07-15 18:00:27
     */
    void offChainCode(String  chainCodeName);
}
