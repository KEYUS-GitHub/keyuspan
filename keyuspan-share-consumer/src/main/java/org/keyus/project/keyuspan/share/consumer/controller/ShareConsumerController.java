package org.keyus.project.keyuspan.share.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.share.consumer.service.ShareConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-08-05  下午2:41
 */
@RestController
@AllArgsConstructor
public class ShareConsumerController {

    private final ShareConsumerService shareConsumerService;

    @PostMapping("/share_file")
    public ServerResponse <ShareRecord> shareFile (@RequestParam("id") Long id, @RequestParam("period_of_validity") Integer days, HttpSession session) {
        return shareConsumerService.shareFile(id, days, session);
    }

    @PostMapping("/save_file_by_share")
    public ServerResponse <FileModel> saveFileByShare (@RequestParam("file_id") Long fileId, @RequestParam("folder_id") Long folderId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return shareConsumerService.saveFileByShare(fileId, folderId, member);
    }

    @PostMapping("/share_folder")
    public ServerResponse shareFolder (@RequestParam("id") Long id, @RequestParam("period_of_validity") Integer days, HttpSession session) {
        return shareConsumerService.shareFolder(id, days, session);
    }

    @PostMapping("/save_folder_by_share")
    public ServerResponse saveFolderByShare (@RequestParam("folder_id") Long folderId, @RequestParam("father_folder_id") Long fatherFolderId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return shareConsumerService.saveFolderByShare(folderId, fatherFolderId, member);
    }

    @GetMapping("/open_share/{url}/{cap_text}")
    public ServerResponse openShare (@PathVariable("url") String url, @PathVariable("cap_text") String capText) throws InterruptedException {
        return shareConsumerService.openShare(url, capText);
    }
}
