package org.keyus.project.keyuspan.common.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
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
@AllArgsConstructor
public class CommonProviderController {

    private final VerificationCodePool verificationCodePool;

    @PostMapping("/create_verification_code")
    public ServerResponse <VerificationCode> createVerificationCode(@RequestParam("key") String key) {
        VerificationCode verifyCode = verificationCodePool.createVerifyCode(key);
        return ServerResponse.createBySuccessWithData(verifyCode);
    }

    @PostMapping("/check_verification_code")
    public ServerResponse checkVerificationCode(@RequestBody VerificationCode verificationCode) {
        boolean check = verificationCodePool.check(verificationCode);
        if (check) {
            return ServerResponse.createBySuccessWithoutData();
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.VERIFICATION_CODE_ERROR.getMessage());
        }
    }
}
