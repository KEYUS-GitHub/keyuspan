package org.keyus.project.keyuspan.folder.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.vo.FolderMessageVO;
import org.keyus.project.keyuspan.folder.consumer.service.FolderConsumerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-08-04  下午11:23
 */
@RestController
@AllArgsConstructor
public class FolderConsumerController {

    private final FolderConsumerService folderConsumerService;

    @PostMapping("/open_folder")
    public ServerResponse openFolderById (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return folderConsumerService.openFolderById(id, member);
    }

    @PostMapping("/create_folder")
    public ServerResponse <VirtualFolder> createFolder (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return folderConsumerService.createFolder(id, folderName, member);
    }

    @PostMapping("/create_main_folder")
    public ServerResponse <VirtualFolder> createMainFolder (@RequestParam("memberId") Long memberId) {
        return folderConsumerService.createMainFolder(memberId);
    }

    @PostMapping("/update_folder_name")
    public ServerResponse <VirtualFolder> updateFolderName (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return folderConsumerService.updateFolderName(id, folderName, member);
    }

    @PostMapping("/delete_folder")
    public ServerResponse <FolderMessageVO> deleteFolder (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return folderConsumerService.deleteFolder(id, member);
    }
}
