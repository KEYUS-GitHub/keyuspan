package org.keyus.project.keyuspan.file.provider.service;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-22  下午10:04
 * 操作文件模型表的service
 */
public interface FileModelService {

    Optional<FileModel> findById(Long id);

    List<FileModel> findByIdIn (Iterable<Long> iterable);

    List<FileModel> findAll();

    <S extends FileModel> List<S> findAll(Example<S> example);

    List<FileModel> findAll(Sort sort);

    <S extends FileModel> List<S> findAll(Example<S> example, Sort sort);

    <S extends FileModel> Page<S> findAll(Example<S> example, Pageable pageable);


    <S extends FileModel> S save(S s);


    <S extends FileModel> List<S> saveAll(Iterable<S> iterable);


    void delete(FileModel fileModel);

    void deleteAll(Iterable<? extends FileModel> iterable);

    void deleteInBatch(Iterable<FileModel> iterable);
}
