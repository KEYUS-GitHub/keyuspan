package org.keyus.project.keyuspan.share.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.common.CommonClientService;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.keyus.project.keyuspan.api.client.service.share.ShareClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.vo.FolderMessageVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author keyus
 * @create 2019-08-05  下午2:41
 */
@RestController
@AllArgsConstructor
public class ShareConsumerController {

    private final ShareClientService shareClientService;

    private final CommonClientService commonClientService;

    private final FileClientService fileClientService;

    private final FolderClientService folderClientService;

    @Resource(name = "shareConsumerExecutor")
    private ThreadPoolExecutor executor;

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
        // -1表示永久分享
        if (days != -1L) {
            builder.dateOfInvalid(LocalDate.now().plusDays(days));
        }
        ShareRecord record = builder.build();
        return shareClientService.save(record);
    }

    @PostMapping("/save_file_by_share")
    public ServerResponse <FileModel> saveFileByShare (@RequestParam("file_id") Long fileId, @RequestParam("folder_id") Long folderId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        ServerResponse<FileModel> serverResponse = fileClientService.findById(fileId);
        if (ServerResponse.isSuccess(serverResponse)) {
            FileModel fileModel = serverResponse.getData();
            FileModel.FileModelBuilder builder = FileModel.builder();
            FileModel newFileModel = builder.memberId(member.getId()).folderId(folderId)
                    .fileExtension(fileModel.getFileExtension())
                    .contentType(fileModel.getContentType()).fileName(fileModel.getFileName())
                    .size(fileModel.getSize()).uri(fileModel.getUri()).build();
            return fileClientService.saveFile(newFileModel);
        } else {
            return serverResponse;
        }
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
    public ServerResponse <VirtualFolder> saveFolderByShare (@RequestParam("folder_id") Long folderId, HttpSession session) {
        // TODO: 19-7-30 通过他人共享的链接来保存分享的文件夹（需要递归操作来保存其子文件夹及文件的数据）
        return null;
    }

    @GetMapping("/open_share/{url}/{cap_text}")
    public ServerResponse openShare (@PathVariable("url") String url, @PathVariable("cap_text") String capText) throws InterruptedException {
        ServerResponse<ShareRecord> response = shareClientService.findByUrl(url);
        if (ServerResponse.isSuccess(response)) {
            ShareRecord record = response.getData();
            if (Objects.equals(record.getCapText(), capText)) {
                // 递归查询 并且要求并发多线程查询
                final CountDownLatch latch = new CountDownLatch(2);
                final List<FileModel> fileModels = new ArrayList<>();
                final List<VirtualFolder> folders = new ArrayList<>();
                executor.execute(() -> {
                    String[] filesIds = record.getFilesIds().split(",");
                    List<Long> fileIdList = new ArrayList<>(filesIds.length);
                    for (String id : filesIds) {
                        fileIdList.add(Long.valueOf(id));
                    }
                    ServerResponse<List<FileModel>> serverResponse = fileClientService.findByIdIn(fileIdList);
                    if (ServerResponse.isSuccess(serverResponse)) {
                        fileModels.addAll(serverResponse.getData());
                    }
                    // 查询结束，减一
                    latch.countDown();
                });

                executor.execute(() -> {
                    String[] foldersIds = record.getFoldersIds().split(",");
                    List<Long> folderIdList = new ArrayList<>(foldersIds.length);
                    for (String id : foldersIds) {
                        folderIdList.add(Long.valueOf(id));
                    }
                    //
                    ServerResponse<List<VirtualFolder>> serverResponse = folderClientService.findByIdIn(folderIdList);
                    if (ServerResponse.isSuccess(serverResponse)) {
                        folders.addAll(serverResponse.getData());
                    }
                    // 查询结束，减一
                    latch.countDown();
                });

                // 等待操作完成
                latch.await();
                return ServerResponse.createBySuccessWithData(FolderMessageVO.getInstance(null, null, folders, fileModels));
            } else {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.CAPTCHA_CHECK_ERROR.getMessage());
            }
        } else {
            return response;
        }
    }

}
