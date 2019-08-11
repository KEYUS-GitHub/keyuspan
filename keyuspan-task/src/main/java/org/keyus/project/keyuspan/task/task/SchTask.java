package org.keyus.project.keyuspan.task.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.client.service.share.ShareClientService;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author keyus
 * @create 2019-08-05  上午10:02
 */
@Slf4j
@Component
@AllArgsConstructor
public class SchTask {

    private final FileClientService fileClientService;

    private final FolderClientService folderClientService;

    private final MemberClientService memberClientService;

    private final ShareClientService shareClientService;

    @Resource(name = "taskServerExecutor")
    private ThreadPoolExecutor executor;

    // 每天凌晨1点的时候，删除那些过期的分享记录
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteShareRecordInRecycleBin () {
        ShareRecord record = ShareRecord.builder()
                .dateOfInvalid(LocalDate.now())
                .build();
        ServerResponse<List<ShareRecord>> serverResponse = shareClientService.findAll(record);
        if (ServerResponse.isSuccess(serverResponse)) {
            shareClientService.deleteInBatch(serverResponse.getData());
        }
    }

    // 每天凌晨3点的时候，删除数据库中的一些记录
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteFilesAndFoldersInRecycleBin() {
        // 并行操作
        executor.execute(folderClientService::deleteFoldersInRecycleBin);
        // 查询所有会员的ID值
        ServerResponse<List<Long>> serverResponse = memberClientService.getMemberIdList();
        List<Long> ids = serverResponse.getData();

        for (Long id : ids) {
            executor.execute(() -> fileClientService.deleteFilesInRecycleBin(id));
        }
    }
}
