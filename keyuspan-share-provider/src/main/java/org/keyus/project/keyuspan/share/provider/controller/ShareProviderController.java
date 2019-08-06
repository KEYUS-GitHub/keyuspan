package org.keyus.project.keyuspan.share.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author keyus
 * @create 2019-08-05  下午2:36
 */
@RestController
@AllArgsConstructor
public class ShareProviderController {

    private final ShareRecordService shareRecordService;

    @PostMapping("/save")
    public ServerResponse <ShareRecord> save (@RequestBody ShareRecord record) {
        ShareRecord save = shareRecordService.save(record);
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @PostMapping("/find_all")
    public ServerResponse <List<ShareRecord>> findAll (@RequestBody ShareRecord record) {
        return ServerResponse.createBySuccessWithData(shareRecordService.findAll(Example.of(record)));
    }

    @PostMapping("find_by_url")
    public ServerResponse <ShareRecord> findByUrl (@RequestBody String url) {
        ShareRecord record = ShareRecord.builder().url(url).build();
        List<ShareRecord> all = shareRecordService.findAll(Example.of(record));
        if (all.size() == 0) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(all.get(0));
        }
    }
}
