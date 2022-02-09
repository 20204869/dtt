package com.example.dtt.service.load.impl;

import com.example.dtt.domain.entity.load.LoadFile;
import com.example.dtt.mapper.load.LoadFileMapper;
import com.example.dtt.service.load.LoadFileService;
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
public class LoadFileServiceImpl implements LoadFileService {

    @Autowired
    private LoadFileMapper fileMapper;

    @Override
    public List<LoadFile> LoadFileList(LoadFile file) {
        return fileMapper.LoadFileList(file);
    }

    @Override
    public int saveFile(LoadFile file) {
        return fileMapper.saveFile(file);
    }

    @Override
    @Transactional
    public int deleteFileByIds(Long[] fileIds) {
        return fileMapper.deleteFileByIds(fileIds);
    }

    @Override
    public List<LoadFile> loadFilesByIds(Long[] fileIds) {
        return fileMapper.loadFilesByIds(fileIds);
    }
}
