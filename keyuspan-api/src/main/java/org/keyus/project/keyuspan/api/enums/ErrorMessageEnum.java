package org.keyus.project.keyuspan.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keyus
 * @create 2019-07-30  上午9:01
 */
@AllArgsConstructor
public enum ErrorMessageEnum {

    VERIFICATION_CODE_ERROR ("验证码错误"),
    FOLDER_NOT_EXIST ("当前操作的文件夹不存在"),
    FILE_NOT_EXIST ("当前操作的文件不存在"),
    USERNAME_OR_PASSWORD_ERROR ("用户名或密码错误"),
    SYSTEM_EXCEPTION ("系统出现异常，请稍后再试"),
    MEMBER_REGISTER_FAIL ("注册会员失败"),
    FILE_DOWNLOAD_EXCEPTION ("文件下载异常");
    @Getter private String message;
}
