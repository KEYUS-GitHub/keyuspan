package org.keyus.project.keyuspan.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应编码枚举类
 * @author keyus
 * @create 2019-07-18  上午7:22
 */
@AllArgsConstructor
public enum ResponseCodeEnum {

    // 普通的success
    SUCCESS(0,"SUCCESS"),
    // 只要不是error，均视为success，异常通过error处理
    ERROR(1,"ERROR"),
    // 查询结果为空值
    NULL_VALUE(2, "NULL_VALUE"),
    // 需要登录
    NEED_LOGIN(10,"NEED_LOGIN");

    @Getter private final int code;
    @Getter private final String desc;
}
