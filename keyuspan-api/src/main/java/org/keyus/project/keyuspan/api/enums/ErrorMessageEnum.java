package org.keyus.project.keyuspan.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keyus
 * @create 2019-07-30  上午9:01
 */
@AllArgsConstructor
public enum ErrorMessageEnum {

    CAPTCHA_CHECK_ERROR ("验证码校验错误"),
    FOLDER_NOT_EXIST ("文件夹数据不存在"),
    FILE_NOT_EXIST ("文件数据不存在"),
    MEMBER_NOT_EXIST ("会员数据不存在"),
    SHARE_RECORD_NOT_EXIST ("分享记录不存在"),
    USERNAME_OR_PASSWORD_ERROR ("用户名或密码错误"),
    USERNAME_REPEAT ("用户名已重复，请更换一个用户名"),
    OUT_OF_MEMBER_STORAGE_SPACE ("会员的存储空间已经超出上限，上传失败"),
    SAVE_FAIL_EXCEPTION ("保存或更新数据失败"),
    DELETE_FAIL_EXCEPTION ("删除数据失败"),
    SYSTEM_EXCEPTION ("系统出现异常，请稍后再试"),
    MEMBER_REGISTER_FAIL ("注册会员失败"),
    FILE_DOWNLOAD_EXCEPTION ("文件下载异常"),
    NETWORK_EXCEPTION ("网络异常"),
    UNAUTHORIZED ("未经授权"),
    CREATE_MAIN_FOLDER_EXCEPTION ("创建用户根目录失败");

    @Getter private String message;
}
