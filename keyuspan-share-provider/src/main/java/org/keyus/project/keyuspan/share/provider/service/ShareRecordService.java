package org.keyus.project.keyuspan.share.provider.service;

import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-08-05  下午2:25
 */
public interface ShareRecordService {

    Optional<ShareRecord> findById(Long id);

    <S extends ShareRecord> List<S> findAll(Example<S> example);

    <S extends ShareRecord> S save(S entity);

    <S extends ShareRecord> List<S> saveAll(Iterable<S> entities);

    void delete(ShareRecord entity);

    void deleteById(Long id);

    void deleteInBatch(Iterable<ShareRecord> entities);
}
