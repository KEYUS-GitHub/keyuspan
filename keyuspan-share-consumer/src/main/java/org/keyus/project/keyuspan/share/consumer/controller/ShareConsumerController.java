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
    public ServerResponse saveFolderByShare (@RequestParam("folder_id") Long folderId, @RequestParam("father_folder_id") Long fatherFolderId, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        saveFolderUseShare(folderId, fatherFolderId, member.getId());
        return ServerResponse.createBySuccessWithoutData();
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

    // 根据ID保存一个被分享文件夹的主逻辑
    private void saveFolderUseShare (Long folderId, Long fatherFolderId, Long memberId) {
        if (Objects.isNull(folderId) || Objects.isNull(fatherFolderId) || Objects.isNull(memberId)) {
            return;
        }

        ServerResponse<VirtualFolder> serverResponse = folderClientService.findById(folderId);
        if (ServerResponse.isSuccess(serverResponse)) {
            VirtualFolder folder = serverResponse.getData();
            VirtualFolder save = VirtualFolder.builder().memberId(memberId)
                    .fatherFolderId(fatherFolderId).deleted(false)
                    .updateDate(LocalDate.now())
                    .virtualFolderName(folder.getVirtualFolderName())
                    .build();
            ServerResponse<VirtualFolder> response = folderClientService.save(save);
            VirtualFolder fatherFolder = response.getData();

            executor.execute(() -> {
                FileModel selectFile = FileModel.builder()
                        .folderId(folderId)
                        .deleted(false)
                        .build();

                ServerResponse<List<FileModel>> fileAll = fileClientService.findAll(selectFile);
                if (ServerResponse.isSuccess(fileAll)) {
                    List<FileModel> allData = fileAll.getData();
                    for (FileModel fm : allData) {
                        fm.setFolderId(fatherFolder.getId());
                        fm.setId(null);
                        fm.setDeleted(false);
                        fm.setMemberId(memberId);
                    }
                    fileClientService.saveFiles(allData);
                }
            });

            executor.execute(() -> {
                VirtualFolder selectFolder = VirtualFolder.builder()
                        .fatherFolderId(folderId)
                        .deleted(false)
                        .build();

                ServerResponse<List<VirtualFolder>> all = folderClientService.findAll(selectFolder);
                if (ServerResponse.isSuccess(all)) {
                    List<VirtualFolder> allData = all.getData();
                    for (VirtualFolder vf : allData) {
                        vf.setFatherFolderId(fatherFolder.getId());
                        vf.setId(null);
                        vf.setDeleted(false);
                        vf.setMemberId(memberId);
                        executor.execute(() -> saveFolderUseShare(vf.getId(), vf.getFatherFolderId(), memberId));
                    }
                    folderClientService.saveAll(allData);
                }
            });
        }
    }
}
