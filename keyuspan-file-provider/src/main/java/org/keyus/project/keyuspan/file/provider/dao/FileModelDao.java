package org.keyus.project.keyuspan.file.provider.dao;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-22  下午9:59
 */
public interface FileModelDao extends JpaRepository<FileModel, Long> {

    @Override
    Optional<FileModel> findById(Long id);

    List<FileModel> findByIdIn (Iterable<Long> iterable);

    @Override
    List<FileModel> findAll();

    @Override
    <S extends FileModel> List<S> findAll(Example<S> example);

    @Override
    List<FileModel> findAll(Sort sort);

    @Override
    <S extends FileModel> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends FileModel> Page<S> findAll(Example<S> example, Pageable pageable);

    @Modifying
    @Override
    <S extends FileModel> S save(S s);

    @Modifying
    @Override
    <S extends FileModel> List<S> saveAll(Iterable<S> iterable);

    @Override
    void delete(FileModel fileModel);

    @Override
    void deleteAll(Iterable<? extends FileModel> iterable);

    @Override
    void deleteInBatch(Iterable<FileModel> iterable);
}
