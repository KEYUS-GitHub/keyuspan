package org.keyus.project.keyuspan.share.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.share.provider.dao.ShareRecordDao;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public ServerResponse<ShareRecord> save (ShareRecord record) {
        ShareRecord save = shareRecordDao.save(record);
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @Override
    public ServerResponse <List<ShareRecord>> findAll (ShareRecord record) {
        return ServerResponse.createBySuccessWithData(shareRecordDao.findAll(Example.of(record)));
    }

    @Override
    public ServerResponse <ShareRecord> findByUrl (String url) {
        ShareRecord record = ShareRecord.builder().url(url).build();
        List<ShareRecord> all = shareRecordDao.findAll(Example.of(record));
        if (all.size() == 0) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(all.get(0));
        }
    }

    @Override
    public ServerResponse <ShareRecord> findById (Long id) {
        Optional<ShareRecord> optional = shareRecordDao.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(() -> ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage()));
    }

    @Override
    public void deleteInBatch (Iterable<ShareRecord> records) {
        shareRecordDao.deleteInBatch(records);
    }
}
