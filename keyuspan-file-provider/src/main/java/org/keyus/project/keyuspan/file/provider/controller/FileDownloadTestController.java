package org.keyus.project.keyuspan.file.provider.controller;

import org.keyus.project.keyuspan.api.util.FileDownloadProxyUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author keyus
 * @create 2019-07-31  上午9:01
 * 测试文件下载用的工具类
 */
@RestController
public class FileDownloadTestController {

    @GetMapping("/download_test")
    public void downloadTest (HttpServletResponse response) throws IOException {
        String url = "http://127.0.0.1/group1/M00/00/00/rBMw2V06a8aAJP-aAA38zbZF49w300.jpg";
        String fileName = "test_download.jpg";
        FileDownloadProxyUtil.proxyAndDownload(response, url, null, fileName);
    }
}
