package org.keyus.project.keyuspan.task.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    // 每天凌晨3点的时候，删除数据库中的记录
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteFilesAndFoldersInRecycleBin() {
        folderClientService.deleteFoldersInRecycleBin();
        fileClientService.deleteFilesInRecycleBin();
        // TODO: 19-8-5 补充修改会员已经使用的存储空间的实现
    }
}
