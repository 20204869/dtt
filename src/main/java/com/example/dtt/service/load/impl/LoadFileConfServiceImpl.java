package com.example.dtt.service.load.impl;

import com.example.dtt.domain.entity.load.LoadFileConf;
import com.example.dtt.mapper.load.LoadFileConfMapper;
import com.example.dtt.service.load.LoadFileConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author reid
 * @date 2021/12/24 15:28
 * @describe
 */
@Service
public class LoadFileConfServiceImpl implements LoadFileConfService {

    @Autowired
    private LoadFileConfMapper confMapper;

    @Override
    public List<LoadFileConf> LoadFileConfList(LoadFileConf conf) {
        return confMapper.LoadFileConfList(conf);
    }

    @Override
    public List<LoadFileConf> getConf() {
        return confMapper.getConf();
    }

    @Override
    @Transactional
    public int saveLoadFileConf(LoadFileConf conf) {
        return confMapper.saveLoadFileConf(conf);
    }

    @Override
    @Transactional
    public int deleteConfByIds(Long[] confIds) {
        return confMapper.deleteConfByIds(confIds);
    }

    @Override
    public int checkConfByHdfsPath(String hdfsPath) {
        return confMapper.checkConfByHdfsPath(hdfsPath);
    }

    @Override
    public int checkConfByHiveTable(String hiveTable) {
        return confMapper.checkConfByHiveTable(hiveTable);
    }

    @Override
    @Transactional
    public int updateConf(LoadFileConf conf) {
        return confMapper.updateConf(conf);
    }

    @Override
    public LoadFileConf selectConfById(Long confId) {
        return confMapper.selectConfById(confId);
    }
}
