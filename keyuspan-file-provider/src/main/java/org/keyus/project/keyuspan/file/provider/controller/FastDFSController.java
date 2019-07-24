package org.keyus.project.keyuspan.file.provider.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.Maps;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author keyus
 * @create 2019-07-23  上午11:02
 * 测试用
 */
@RestController
@RequestMapping("/fdfs")
public class FastDFSController {

    private final FastFileStorageClient storageClient;

    public FastDFSController(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    /**
     * 是从浏览器提交过来
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Map upload(@RequestParam("file") MultipartFile file) throws IOException {
        String extName = FilenameUtils.getExtension(file.getOriginalFilename());
        StorePath sp = storageClient.uploadFile("group1", file.getInputStream(), file.getSize(), extName);
        Map<String, Object> map = Maps.newHashMap();
        map.put("msg", "OK");
        map.put("path", sp.getFullPath());
        return map;
    }
}
