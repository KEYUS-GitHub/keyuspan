package org.keyus.project.keyuspan.api.util;

import org.keyus.project.keyuspan.api.pojo.FileModel;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.util.Date;

/**
 * @author keyus
 * @create 2019-07-25  下午2:33
 */
public class FileModelUtil {

    public static FileModel changeToFileModel (Long memberId, MultipartFile file, String uri) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        double size = (double) file.getSize() / 1024.0; // 获得字节数，单位是KB
        String contentType = file.getContentType();
        // 创造对象
        FileModel fileModel = new FileModel();
        fileModel.setFileExtension(fileExtension);
        fileModel.setFileName(fileName);
        fileModel.setSize(size);
        fileModel.setContentType(contentType);
        fileModel.setDeleted(false);
        fileModel.setUpdateDate(new Date());
        fileModel.setMemberId(memberId);
        fileModel.setDateOfRecovery(null);
        fileModel.setUri(uri);
        return fileModel;
    }
}

