package org.keyus.project.keyuspan.share.provider.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
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
@AllArgsConstructor
public class ShareRecordServiceImpl implements ShareRecordService, ITxTransaction {

    private final ShareRecordDao shareRecordDao;

    @TxTransaction
    @Transactional
    @Override
    public ShareRecord save (ShareRecord record) {
        return shareRecordDao.save(record);
    }

    @Override
    public List<ShareRecord> findAll (ShareRecord record) {
        return shareRecordDao.findAll(Example.of(record));
    }

    @Override
    public ShareRecord findByUrl (String url) {
        ShareRecord record = ShareRecord.builder().url(url).build();
        List<ShareRecord> all = shareRecordDao.findAll(Example.of(record));
        return all.get(0);
    }

    @Override
    public ShareRecord findById (Long id) {
        Optional<ShareRecord> optional = shareRecordDao.findById(id);
        return optional.orElseGet(ShareRecord::new);
    }

    @TxTransaction
    @Transactional
    @Override
    public void deleteInBatch (Iterable<ShareRecord> records) {
        shareRecordDao.deleteInBatch(records);
    }
}
