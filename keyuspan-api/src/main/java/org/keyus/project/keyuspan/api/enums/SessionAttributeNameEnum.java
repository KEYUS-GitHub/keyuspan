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
    LOGIN_MEMBER ("MEMBER");
    @Getter private String name;
}
