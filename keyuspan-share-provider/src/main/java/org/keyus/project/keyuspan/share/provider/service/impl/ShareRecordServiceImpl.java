package org.keyus.project.keyuspan.share.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.share.provider.dao.ShareRecordDao;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-08-05  下午2:26
 */
@Service
@Transactional
@AllArgsConstructor
public class ShareRecordServiceImpl implements ShareRecordService {

    private final ShareRecordDao shareRecordDao;

    @Override
    public Optional<ShareRecord> findById(Long id) {
        return shareRecordDao.findById(id);
    }

    @Override
    public <S extends ShareRecord> List<S> findAll(Example<S> example) {
        return shareRecordDao.findAll(example);
    }

    @Override
    public <S extends ShareRecord> S save(S entity) {
        return shareRecordDao.save(entity);
    }

    @Override
    public <S extends ShareRecord> List<S> saveAll(Iterable<S> entities) {
        return shareRecordDao.saveAll(entities);
    }

    @Override
    public void delete(ShareRecord entity) {
        shareRecordDao.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        shareRecordDao.deleteById(id);
    }

    @Override
    public void deleteInBatch(Iterable<ShareRecord> entities) {
        shareRecordDao.deleteInBatch(entities);
    }
}
