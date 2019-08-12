package org.keyus.project.keyuspan.file.provider.service;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author keyus
 * @create 2019-07-22  下午10:04
 * 操作文件模型表的service
 */
public interface FileModelService {

    ServerResponse<FileModel> findById (Long id);

    ServerResponse <List<FileModel>> findByIdIn (Iterable<Long> iterable);

    ServerResponse <FileModel> saveFile (FileModel fileModel);

    ServerResponse <List<FileModel>> saveFiles (List<FileModel> list);

    ServerResponse <List<FileModel>> findAll (FileModel fileModel);

    ServerResponse <List<FileModel>> getFilesByFolderId(Long id);

    ServerResponse <List<FileModel>> deleteFilesInRecycleBin (Long memberId , HttpSession session) throws ExecutionException, InterruptedException;

}
