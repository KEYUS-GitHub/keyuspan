package org.keyus.project.keyuspan.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author keyus
 * @create 2019-07-18  下午10:05
 */
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    @Getter private String key;
    @Getter private String answer;

    public static VerificationCode create(String key, String answer) {
        return new VerificationCode(key, answer);
    }
}
