package org.keyus.project.keyuspan.file.provider.service.impl;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-22  下午10:08
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final FastFileStorageClient storageClient;

    private final FdfsWebServer fdfsWebServer;

    public FileServiceImpl(FastFileStorageClient storageClient, FdfsWebServer fdfsWebServer) {
        this.storageClient = storageClient;
        this.fdfsWebServer = fdfsWebServer;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUri(storePath);
    }

    /**
     * 上传文件
     * @param files 文件对象的集合集合
     * @return 文件访问地址的集合
     * @throws IOException
     */
    @Override
    public String[] uploadFiles(List<MultipartFile> files) throws IOException {
        int size = files.size();
        String[] uris = new String[size];
        for (int i = 0; i < size; i++) {
            MultipartFile file = files.get(i);
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
            uris[i] = getResAccessUri(storePath);
        }
        return uris;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    @Override
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream (file);
        StorePath storePath = storageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()),null);
        return getResAccessUri(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    @Override
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension,null);
        return getResAccessUri(storePath);
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    @Override
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public String getWebServerUrl() {
        return fdfsWebServer.getWebServerUrl();
    }

    // 封装图片完整URL地址
    private String getResAccessUri(StorePath storePath) {
        return storePath.getFullPath();
    }
}
