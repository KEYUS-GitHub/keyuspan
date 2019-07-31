package org.keyus.project.keyuspan.api.client.service.common;

import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VerificationCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author keyus
 * @create 2019-07-29  下午2:37
 */
@FeignClient(value = "keyuspan-common-provider", fallbackFactory = CommonClientServiceFallbackFactory.class)
public interface CommonClientService {

    @PostMapping("/create_verification_code")
    ServerResponse <VerificationCode> createVerificationCode(@RequestParam("key") String key);

    @PostMapping("/check_verification_code")
    ServerResponse checkVerificationCode(@RequestBody VerificationCode verificationCode);
}
