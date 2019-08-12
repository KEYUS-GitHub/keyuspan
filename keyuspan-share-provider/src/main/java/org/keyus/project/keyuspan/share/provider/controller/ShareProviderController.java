package org.keyus.project.keyuspan.share.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return shareRecordService.save(record);
    }

    @PostMapping("/find_all")
    public ServerResponse <List<ShareRecord>> findAll (@RequestBody ShareRecord record) {
        return shareRecordService.findAll(record);
    }

    @PostMapping("find_by_url")
    public ServerResponse <ShareRecord> findByUrl (@RequestBody String url) {
        return shareRecordService.findByUrl(url);
    }

    @PostMapping("/find_by_id")
    public ServerResponse <ShareRecord> findById (@RequestBody Long id) {
        return shareRecordService.findById(id);
    }

    @PostMapping("/delete_in_batch")
    public void deleteInBatch (@RequestBody Iterable<ShareRecord> records) {
        shareRecordService.deleteInBatch(records);
    }
}
