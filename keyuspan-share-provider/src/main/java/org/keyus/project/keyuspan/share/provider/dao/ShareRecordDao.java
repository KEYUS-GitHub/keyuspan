package org.keyus.project.keyuspan.share.provider.dao;

import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-08-05  下午2:19
 */

public interface ShareRecordDao extends JpaRepository<ShareRecord, Long> {

    @Override
    Optional<ShareRecord> findById(Long id);

    @Override
    <S extends ShareRecord> List<S> findAll(Example<S> example);

    @Modifying
    @Override
    <S extends ShareRecord> S save(S entity);

    @Modifying
    @Override
    <S extends ShareRecord> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(ShareRecord entity);

    @Override
    void deleteById(Long id);

    @Override
    void deleteInBatch(Iterable<ShareRecord> entities);
}
