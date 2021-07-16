package io.github.ecsoya.fabric.explorer.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.ecsoya.fabric.explorer.repository.dao.ChainCodeDAO;
import io.github.ecsoya.fabric.explorer.repository.entity.ChainCodeEntity;
import io.github.ecsoya.fabric.explorer.repository.mapper.ChainCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * The type Chain code dao.
 *
 * @author XieXiongXiong
 * @date 2021 -07-15
 */
public class ChainCodeDAOImpl implements ChainCodeDAO {
    private final ChainCodeMapper chainCodeMapper;

    public ChainCodeDAOImpl(ChainCodeMapper chainCodeMapper) {
        this.chainCodeMapper = chainCodeMapper;
    }
    @Override
    public List<ChainCodeEntity> listChainCode(String chainCodeName, Integer currentPage, Integer pageSize) {
        currentPage = (currentPage -1) * pageSize;
        return chainCodeMapper.selectChainCodeEntities(chainCodeName,currentPage,pageSize);
    }

    @Override
    public void insertChainCode(ChainCodeEntity entity) {
         chainCodeMapper.insert(entity);
    }

    @Override
    public List<ChainCodeEntity> listChainCodeVersion(String chainCodeName, Integer currentPage, Integer pageSize) {
        currentPage = (currentPage -1) * pageSize;
        return chainCodeMapper.selectHistory(chainCodeName,currentPage,pageSize);
    }

    @Override
    public Long nextSeq(String chainCodeName) {
        return chainCodeMapper.nextSeq(chainCodeName);
    }

    @Override
    public void offChainCode(String  chainCodeName) {
        chainCodeMapper.offChainCode(chainCodeName);
    }

}
