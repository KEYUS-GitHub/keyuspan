package org.keyus.project.keyuspan.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keyus
 * @create 2019-08-13  下午2:21
 */
@AllArgsConstructor
public enum HttpHeaderValueEnum {

    APPLICATION_JSON ("application/json;charset=UTF-8");
    @Getter private String value;
}
