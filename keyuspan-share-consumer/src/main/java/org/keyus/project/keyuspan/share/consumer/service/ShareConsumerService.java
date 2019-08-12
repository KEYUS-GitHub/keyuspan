package org.keyus.project.keyuspan.share.consumer.service;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-08-12  下午2:35
 */
public interface ShareConsumerService {

    ServerResponse<ShareRecord> shareFile (Long id, Integer days, HttpSession session);

    ServerResponse <FileModel> saveFileByShare (Long fileId, Long folderId, Member member);

    ServerResponse shareFolder (Long id, Integer days, HttpSession session);

    ServerResponse saveFolderByShare (Long folderId, Long fatherFolderId, Member member);

    ServerResponse openShare (String url, String capText) throws InterruptedException;
}
