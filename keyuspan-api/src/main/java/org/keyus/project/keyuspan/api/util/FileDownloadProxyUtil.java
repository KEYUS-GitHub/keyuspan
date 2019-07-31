package org.keyus.project.keyuspan.api.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author keyus
 * @create 2019-07-31  上午9:25
 * 文件下载代理工具类
 */
@Slf4j
public class FileDownloadProxyUtil {

    private static final String FILE_DOWNLOAD_CONTENT_TYPE = "multipart/form-data";

    public static void proxyAndDownload (HttpServletRequest request, HttpServletResponse response, String urlAddress, String contentType, String fileName) throws IOException {
        InputStream input = null;
        ServletOutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            input = connection.getInputStream();
            output = response.getOutputStream();

            if (contentType != null) {
                response.setContentType(contentType);
            } else {
                // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
                response.setContentType(FILE_DOWNLOAD_CONTENT_TYPE);
            }

            // 返回客户端浏览器的版本号、类型
            String agent = request.getHeader("USER-AGENT");

            // 客户端下载文件时，文件的名称
            String downloadName;

            if (agent.contains("MSIE") || agent.contains("Trident")) {
                // IE内核浏览器的处理
                downloadName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            } else {
                // 非IE内核浏览器的处理：
                downloadName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }

            response.setHeader("Content-disposition", "attachment; filename=\"" + downloadName + "\"");

            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int length;
            // 使用一个输入流从buffer里把数据读取出来
            while ((length = input.read(buffer)) != -1 ){
                // 用输出流往buffer里写入数据，中间参数代表从哪个位
                // 置开始读，length代表读取的长度
                output.write(buffer, 0, length);
            }

        } catch (Exception ex) {
            log.error("File Download Proxy URL File error, exception = {}", ex.toString());
            throw ex;
        } finally {
            // 释放所有资源
            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.flush();
                output.close();
            }

            if (connection != null) {
                connection.disconnect();
            }

        }
    }

    public static String getRealUrl (String webServerUrl, String uri) {
        return "http://" + webServerUrl + '/' + uri;
    }
}
