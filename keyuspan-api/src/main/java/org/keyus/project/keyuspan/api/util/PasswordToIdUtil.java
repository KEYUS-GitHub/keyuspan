package org.keyus.project.keyuspan.api.util;

/**
 * @author keyus
 * @create 2019-07-30  下午4:16
 * @version 1.0
 * 数据库任意表的ID值与密码值之间进行转换的工具类，用于保护系统安全
 * 本工具类的第一个版本使用base64实现
 */
public class PasswordToIdUtil {

    public static String encrypt (Long id) {
        return EncryptUtil.encryptBASE64(String.valueOf(id).getBytes());
    }

    public static Long decrypt (String password) {
        return Long.valueOf(new String(EncryptUtil.decryptBASE64(password)));
    }
}
