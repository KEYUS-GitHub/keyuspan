package org.keyus.project.keyuspan.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keyus
 * @create 2019-08-05  上午10:14
 */
@AllArgsConstructor
public enum TaskCronExpressionEnum {

    DELETE_FILES_AND_FOLDERS ("0 0 3 * * ?");

    @Getter private String expression;
}
