//package org.keyus.project.keyuspan.file.provider.client;
//
//import com.github.tobato.fastdfs.conn.FdfsWebServer;
//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
//import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.Charsets;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
///**
// * @author keyus
// * @create 2019-07-23  上午10:46
// */
//@Slf4j
//@Component
//public class FastDFSClient {
//
//    private final FastFileStorageClient storageClient;
//
//    private final FdfsWebServer fdfsWebServer;
//
//    public FastDFSClient(FastFileStorageClient storageClient, FdfsWebServer fdfsWebServer) {
//        this.storageClient = storageClient;
//        this.fdfsWebServer = fdfsWebServer;
//    }
//
//    /**
//     * 上传文件
//     * @param file 文件对象
//     * @return 文件访问地址
//     * @throws IOException
//     */
//    public String uploadFile(MultipartFile file) throws IOException {
//        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
//        return getResAccessUrl(storePath);
//    }
//
//    /**
//     * 上传文件
//     * @param file 文件对象
//     * @return 文件访问地址
//     * @throws IOException
//     */
//    public String uploadFile(File file) throws IOException {
//        FileInputStream inputStream = new FileInputStream (file);
//        StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()),null);
//        return getResAccessUrl(storePath);
//    }
//
//    /**
//     * 将一段字符串生成一个文件上传
//     * @param content 文件内容
//     * @param fileExtension
//     * @return
//     */
//    public String uploadFile(String content, String fileExtension) {
//        byte[] buff = content.getBytes(Charsets.UTF_8);
//        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
//        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension,null);
//        return getResAccessUrl(storePath);
//    }
//
//    // 封装图片完整URL地址
//    private String getResAccessUrl(StorePath storePath) {
//        return fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
//    }
//
//    /**
//     * 下载文件
//     * @param fileUrl 文件url
//     * @return
//     */
//    public byte[] download(String fileUrl) {
//        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
//        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
//        return storageClient.downloadFile(group, path, new DownloadByteArray());
//    }
//
//    /**
//     * 删除文件
//     * @param fileUrl 文件访问地址
//     * @return
//     */
//    public void deleteFile(String fileUrl) {
//        if (StringUtils.isEmpty(fileUrl)) {
//            return;
//        }
//        try {
//            StorePath storePath = StorePath.praseFromUrl(fileUrl);
//            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
//        } catch (FdfsUnsupportStorePathException e) {
//            log.warn(e.getMessage());
//        }
//    }
//}
