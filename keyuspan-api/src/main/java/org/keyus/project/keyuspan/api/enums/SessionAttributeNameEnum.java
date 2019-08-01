package org.keyus.project.keyuspan.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keyus
 * @create 2019-07-30  上午8:56
 */
@AllArgsConstructor
public enum SessionAttributeNameEnum {

    // 用于保存登录会员的会员信息
    LOGIN_MEMBER ("MEMBER"),
    // 保存用于文本的验证码的key
    CAPTCHA_FOR_TEXT ("CAPTCHA_FOR_TEXT"),
    // 保存用于输出图片的验证码的key
    CAPTCHA_FOR_IMAGE ("CAPTCHA_FOR_IMAGE");

    @Getter private String name;
}
