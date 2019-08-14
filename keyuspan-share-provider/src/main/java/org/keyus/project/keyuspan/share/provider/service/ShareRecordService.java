package org.keyus.project.keyuspan.share.provider.service;

import org.keyus.project.keyuspan.api.po.ShareRecord;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-05  下午2:25
 */
public interface ShareRecordService {

    ShareRecord save (ShareRecord record);

    List<ShareRecord> findAll (ShareRecord record);

    ShareRecord findByUrl (String url);

    ShareRecord findById (Long id);

    void deleteInBatch (Iterable<ShareRecord> records);
}
