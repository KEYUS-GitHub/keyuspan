package org.keyus.project.keyuspan.share.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.share.provider.dao.ShareRecordDao;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author keyus
 * @create 2019-08-05  下午2:26
 */
@Service
@Transactional
@AllArgsConstructor
public class ShareRecordServiceImpl implements ShareRecordService {

    private final ShareRecordDao shareRecordDao;


}
