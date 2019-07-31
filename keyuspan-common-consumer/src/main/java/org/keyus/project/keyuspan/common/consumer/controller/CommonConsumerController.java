package org.keyus.project.keyuspan.common.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.common.CommonClientService;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keyus
 * @create 2019-07-21  下午4:40
 */
@RestController
@AllArgsConstructor
public class CommonConsumerController {

    private final CommonClientService commonClientService;

    @PostMapping("/create_verification_code")
    public ServerResponse createVerificationCode(@RequestParam("key") String key) {
        return commonClientService.createVerificationCode(key);
    }

}
