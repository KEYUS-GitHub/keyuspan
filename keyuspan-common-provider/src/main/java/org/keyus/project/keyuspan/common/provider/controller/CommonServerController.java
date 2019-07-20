package org.keyus.project.keyuspan.common.provider.controller;

import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VerificationCode;
import org.keyus.project.keyuspan.api.util.VerificationCodePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author keyus
 * @create 2019-07-18  下午9:43
 */
@RestController
public class CommonServerController {

    @Autowired
    private VerificationCodePool verificationCodePool;

    @PostMapping("/create_verification_code")
    public ServerResponse createVerificationCode(@RequestBody String key) {
        VerificationCode verifyCode = verificationCodePool.createVerifyCode(key);
        return ServerResponse.createBySuccessWithData(verifyCode);
    }

    @PostMapping("/check_verification_code")
    public ServerResponse checkVerificationCode(@RequestBody VerificationCode verificationCode) {
        boolean check = verificationCodePool.check(verificationCode);
        if (check) {
            return ServerResponse.createBySuccessWithoutData();
        } else {
            return ServerResponse.createByErrorWithMessage("验证码错误");
        }
    }
}
