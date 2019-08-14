package org.keyus.project.keyuspan.api.client.service.share;

import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-05  下午2:42
 */
@FeignClient(value = "keyuspan-share-provider")
public interface ShareClientService {

    @PostMapping("/save")
    ShareRecord save (@RequestBody ShareRecord record);

    @PostMapping("/find_all")
    List<ShareRecord> findAll (@RequestBody ShareRecord record);

    @PostMapping("find_by_url")
    ShareRecord findByUrl (@RequestBody String url);

    @PostMapping("/find_by_id")
    ShareRecord findById (@RequestBody Long id);

    @PostMapping("/delete_in_batch")
    void deleteInBatch (@RequestBody Iterable<ShareRecord> records);

}
