package io.github.ecsoya.fabric.explorer.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.ecsoya.fabric.explorer.repository.entity.ChainCodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * The interface Chaincode mapper.
 *
 * @author XieXiongXiong
 * @date 2021 -07-15
 */
public interface ChainCodeMapper extends BaseMapper<ChainCodeEntity> {

    /**
     * Next seq long.
     *
     * @param chainCodeName the chain code name
     * @return the long
     * @author XieXiongXiong
     * @date 2021 -07-15 11:12:41
     */
    Long nextSeq(@Param("chainCodeName")String chainCodeName);


    /**
     * Select chain code entities list.
     *
     * @param chainCodeName the chain code name
     * @param offset        the offset
     * @param size          the size
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-15 17:11:28
     */
    List<ChainCodeEntity> selectChainCodeEntities(@Param("chainCodeName")String chainCodeName,
                                                  @Param("offset")Integer offset,
                                                  @Param("size")Integer size);

    /**
     * Select history list.
     *
     * @param chainCodeName the chain code name
     * @param offset        the offset
     * @param size          the size
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-15 17:11:28
     */
    List<ChainCodeEntity> selectHistory(@Param("chainCodeName")String chainCodeName,
                                                  @Param("offset")Integer offset,
                                                  @Param("size")Integer size);

    /**
     * Off chain code.
     *
     * @param chainCodeName the chain code name
     * @author XieXiongXiong
     * @date 2021 -07-15 18:03:13
     */
    void offChainCode(String chainCodeName);
}
