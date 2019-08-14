package org.keyus.project.keyuspan.folder.consumer.service;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.vo.FolderMessageVO;
import org.keyus.project.keyuspan.api.vo.FolderVO;

/**
 * @author keyus
 * @create 2019-08-12  下午1:33
 */
public interface FolderConsumerService {

    ServerResponse <FolderMessageVO> openFolderById (Long id, Member member);

    ServerResponse <VirtualFolder> createFolder (Long id, String folderName, Member member);

    ServerResponse <VirtualFolder> createMainFolder (Long memberId);

    ServerResponse <FolderVO> updateFolderName (Long id, String folderName, Member member);

    ServerResponse <FolderMessageVO> deleteFolder (Long id, Member member);
}
