package org.keyus.project.keyuspan.share.provider.service;

import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-05  下午2:25
 */
public interface ShareRecordService {

    ServerResponse<ShareRecord> save (ShareRecord record);

    ServerResponse <List<ShareRecord>> findAll (ShareRecord record);

    ServerResponse <ShareRecord> findByUrl (String url);

    ServerResponse <ShareRecord> findById (Long id);

    void deleteInBatch (Iterable<ShareRecord> records);
}
