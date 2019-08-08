package org.keyus.project.keyuspan.file.provider;

import org.junit.Test;

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
        System.out.println(9 & 1);
    }
}
