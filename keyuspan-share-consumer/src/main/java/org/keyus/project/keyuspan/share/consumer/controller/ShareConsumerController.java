package org.keyus.project.keyuspan.share.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.common.CommonClientService;
import org.keyus.project.keyuspan.api.client.service.share.ShareClientService;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author keyus
 * @create 2019-08-05  下午2:41
 */
@RestController
@AllArgsConstructor
public class ShareConsumerController {

    private final ShareClientService shareClientService;

    private final CommonClientService commonClientService;

    @PostMapping("/share_file")
    public ServerResponse <ShareRecord> shareFile (@RequestParam("id") Long id, @RequestParam("period_of_validity") Integer days, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        // 本次分享产生的URL的值，使用UUID避免重复
        String url = UUID.randomUUID().toString();
        commonClientService.createCapText();
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_TEXT.getName());
        // 使用建造者模式创建实例来保存入库
        ShareRecord.ShareRecordBuilder builder = ShareRecord.builder();
        builder.memberId(member.getId()).capText(capText)
                .filesIds(String.valueOf(id)).url(url);
        if (days != -1L) {
            builder.dateOfInvalid(LocalDate.now().plusDays(days));
        }
        ShareRecord record = builder.build();
        return shareClientService.save(record);
    }

    @PostMapping("/save_file_by_share")
    public ServerResponse <FileModel> saveFileByShare (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 通过他人共享的链接来保存分享的文件夹
        return null;
    }

    @PostMapping("/share_folder")
    public ServerResponse shareFolder (@RequestParam("id") Long id, @RequestParam("period_of_validity") Integer days, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        // 本次分享产生的URL的值，使用UUID避免重复
        String url = UUID.randomUUID().toString();
        commonClientService.createCapText();
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_TEXT.getName());
        // 使用建造者模式创建实例来保存入库
        ShareRecord.ShareRecordBuilder builder = ShareRecord.builder();
        builder.memberId(member.getId()).capText(capText)
                .foldersIds(String.valueOf(id)).url(url);
        if (days != -1L) {
            builder.dateOfInvalid(LocalDate.now().plusDays(days));
        }
        ShareRecord record = builder.build();
        return shareClientService.save(record);
    }

    @PostMapping("/save_folder_by_share")
    public ServerResponse <VirtualFolder> saveFolderByShare (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 通过他人共享的链接来保存分享的文件夹
        return null;
    }

    @GetMapping("/open_share/{url}")
    public ServerResponse openShare (@PathVariable("url") String url) {
        // TODO: 19-8-6 通过URL打开一个分享连接的业务
        ServerResponse<ShareRecord> response = shareClientService.findByUrl(url);
        if (ServerResponse.isSuccess(response)) {
            ShareRecord record = response.getData();
            // TODO: 19-8-6 创建一个VO对象，放入所分享的文件夹及文件的相关
            //  信息，使得前端可以填充数据，展示页面
            return null;
        } else {
            return response;
        }
    }

}
