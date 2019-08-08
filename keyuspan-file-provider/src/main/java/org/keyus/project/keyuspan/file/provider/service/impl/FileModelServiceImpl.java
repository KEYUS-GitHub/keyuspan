package org.keyus.project.keyuspan.file.provider.service.impl;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.file.provider.dao.FileModelDao;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-22  下午10:05
 */
@Service
@Transactional
public class FileModelServiceImpl implements FileModelService {

    private final FileModelDao fileModelDao;

    public FileModelServiceImpl(FileModelDao fileModelDao) {
        this.fileModelDao = fileModelDao;
    }

    @Override
    public Optional<FileModel> findById(Long id) {
        return fileModelDao.findById(id);
    }

    @Override
    public List<FileModel> findByIdIn(Iterable<Long> iterable) {
        return fileModelDao.findByIdIn(iterable);
    }

    @Override
    public List<FileModel> findAll() {
        return fileModelDao.findAll();
    }

    @Override
    public <S extends FileModel> List<S> findAll(Example<S> example) {
        return fileModelDao.findAll(example);
    }

    @Override
    public List<FileModel> findAll(Sort sort) {
        return fileModelDao.findAll(sort);
    }

    @Override
    public <S extends FileModel> List<S> findAll(Example<S> example, Sort sort) {
        return fileModelDao.findAll(example, sort);
    }

    @Override
    public <S extends FileModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return fileModelDao.findAll(example, pageable);
    }

    @Override
    public <S extends FileModel> S save(S s) {
        return fileModelDao.save(s);
    }

    @Override
    public <S extends FileModel> List<S> saveAll(Iterable<S> iterable) {
        return fileModelDao.saveAll(iterable);
    }

    @Override
    public void delete(FileModel fileModel) {
        fileModelDao.delete(fileModel);
    }

    @Override
    public void deleteAll(Iterable<? extends FileModel> iterable) {
        fileModelDao.deleteAll(iterable);
    }

    @Override
    public void deleteInBatch(Iterable<FileModel> iterable) {
        fileModelDao.deleteInBatch(iterable);
    }
}
