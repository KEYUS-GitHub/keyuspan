package org.keyus.project.keyuspan.file.provider;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * @author keyus
 * @create 2019-07-30  下午2:37
 */
public class AppTest {

    @Test
    public void test1() {
        String path = "/home/keyus";
        String newName = "hello";
        String oldName = "keyus";
        path = path.substring(0, path.length() - oldName.length());
        path = path + newName;
        System.out.println(path);
    }

    @Test
    public void test2() {
        String fileName = "keyus.png";
        System.out.println(fileName.substring(fileName.indexOf('.') + 1));
    }

    @Test
    public void test3() {
        System.out.println(StandardCharsets.UTF_8.name());
    }

    @Test
    public void test4() {
        LocalDate now = LocalDate.now();
        // 加上当前用户的回收站保存日期
        LocalDate collectionDate = now.plusDays(1);
        System.out.println(collectionDate.toString());
    }
}
