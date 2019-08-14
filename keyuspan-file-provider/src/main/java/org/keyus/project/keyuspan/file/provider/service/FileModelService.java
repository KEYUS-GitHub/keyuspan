package org.keyus.project.keyuspan.file.provider.service;

import org.keyus.project.keyuspan.api.po.FileModel;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author keyus
 * @create 2019-07-22  下午10:04
 * 操作文件模型表的service
 */
public interface FileModelService {

    Optional<FileModel> findById (Long id);

    List<FileModel> findByIdIn (Iterable<Long> iterable);

    FileModel saveFile (FileModel fileModel);

    List<FileModel> saveFiles (List<FileModel> list);

    List<FileModel> findAll (FileModel fileModel);

    List<FileModel> getFilesByFolderId(Long id);

    List<FileModel> deleteFilesInRecycleBin (Long memberId , HttpSession session) throws ExecutionException, InterruptedException;

}
