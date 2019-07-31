package org.keyus.project.keyuspan.api.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author keyus
 * @create 2019-07-31  上午9:25
 * 文件下载代理工具类
 */
@Slf4j
public class FileDownloadProxyUtil {

    public static void proxyAndDownload (HttpServletResponse response, String urlAddress, String contentType, String fileName) throws IOException {
        InputStream is = null;
        ServletOutputStream os = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            is = connection.getInputStream();
            os = response.getOutputStream();

            if (contentType != null) {
                response.setContentType(contentType);
            } else {
                // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
                response.setContentType("multipart/form-data");
            }

            if (fileName != null) {
                response.setHeader("Content-disposition", "attachment; filename=\""
                        + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)+"\"");
            }

            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1 ){
                // 用输出流往buffer里写入数据，中间参数代表从哪个位
                // 置开始读，len代表读取的长度
                os.write(buffer, 0, len);
            }

        } catch (Exception e) {
            log.error("File Download Proxy URL File error, e = {}", e.getMessage());
            throw e;
        } finally {

            if (is != null) {
                is.close();
            }

            if (os != null) {
                os.flush();
                os.close();
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
