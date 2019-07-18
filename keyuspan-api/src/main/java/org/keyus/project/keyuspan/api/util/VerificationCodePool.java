package org.keyus.project.keyuspan.api.util;

import com.google.common.collect.Maps;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @author keyus
 * @create 2019-07-18  下午4:41
 * 用于生成验证码的池子
 */
public class VerificationCodePool {

    // 暂且用ConcurrentHashMap代替redis存储
    private static final Map<String, String> POOL = Maps.newConcurrentMap();
    // 使用到Algerian字体，系统里没有的话需要安装字体，字体
    // 只显示大写，去掉了几个容易混淆的字符
    private static final String VERIFY_CODES =
            "23456789abcdefghmnqrstuvwyABCDEFGHJKLMNPQRSTUVWXYZ";

    @Setter private int codeSize = 4;

    private String generateVerifyCode(){
        int codesLen = VERIFY_CODES.length();
        Random random = new Random(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder(codeSize);
        for (int i = 0; i < codeSize; i++) {
            builder.append(VERIFY_CODES.charAt(random.nextInt(codesLen - 1)));
        }
        return builder.toString();
    }

    public String getVerifyCode(String key) {
        String verifyCode = generateVerifyCode();
        POOL.put(key, verifyCode);
        return verifyCode;
    }

    public boolean check(String key, String answer) {
        return Objects.equals(POOL.get(key), answer);
    }
}
