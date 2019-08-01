package org.keyus.project.keyuspan.api.util;

import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-25  下午2:33
 */
public class FileModelUtil {

    public static FileModel changeToFileModel (Long memberId, MultipartFile file, String uri, Long folderId) {
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
        fileModel.setUpdateDate(LocalDate.now());
        fileModel.setMemberId(memberId);
        fileModel.setFolderId(folderId);
        fileModel.setDateOfRecovery(null);
        fileModel.setUri(uri);
        return fileModel;
    }

    public static List<FileModel> changeToFileModels (Long memberId, List<MultipartFile> files, String[] uris, Long folderId) throws Exception {
        if (files.size() != uris.length) {
            throw new Exception(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
        }
        List<FileModel> res = new ArrayList<>();
        int size = uris.length;
        for (int i = 0; i < size; i++) {
            FileModel fileModel = FileModelUtil.changeToFileModel(memberId, files.get(i), uris[i], folderId);
            res.add(fileModel);
        }
        return res;
    }
}

