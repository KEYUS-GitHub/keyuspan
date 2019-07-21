package org.keyus.project.keyuspan.common.consumer.controller;

import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author keyus
 * @create 2019-07-21  下午4:40
 */
@RestController
public class CommonConsumerController {

    private static final String COMMON_REST_URL_PREFIX = "http://keyuspan-common-provider";

    private final RestTemplate restTemplate;

    public CommonConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/create_verification_code")
    public ServerResponse createVerificationCode(@RequestParam("key") String key) {
        return restTemplate.postForObject(COMMON_REST_URL_PREFIX + "/create_verification_code", key, ServerResponse.class);
    }

}
