package org.keyus.project.keyuspan.file.provider.service.impl;

import org.keyus.project.keyuspan.file.provider.dao.FileModelDao;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.springframework.stereotype.Service;

/**
 * @author keyus
 * @create 2019-07-22  下午10:05
 */
@Service
public class FileModelServiceImpl implements FileModelService {

    private final FileModelDao fileModelDao;

    public FileModelServiceImpl(FileModelDao fileModelDao) {
        this.fileModelDao = fileModelDao;
    }

    
}
