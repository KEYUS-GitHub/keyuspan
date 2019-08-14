package org.keyus.project.keyuspan.api.client.service.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author keyus
 * @create 2019-07-29  下午2:37
 */
@FeignClient(value = "keyuspan-common")
public interface CommonClientService {

    @GetMapping("/create_text")
    void createCapText ();
}
