package org.keyus.project.keyuspan.api.vo;

import lombok.*;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.VirtualFolder;

import java.io.Serializable;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-28  下午4:36
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FolderMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前的文件夹的信息
    private FolderVO currentFolder;

    // 当前文件夹所在的虚拟路径
    private String currentPath;

    // 当前文件夹下的子文件夹的list
    private List<FolderVO> folders;

    // 当前文件夹下的所有文件的list
    private List<FileModelVO> files;

    public static FolderMessageVO getInstance(FolderVO currentFolder, String currentPath, List<VirtualFolder> virtualFolders, List<FileModel> fileModels) {
        return new FolderMessageVO(currentFolder, currentPath, FolderVO.getInstances(virtualFolders), FileModelVO.getInstances(fileModels));
    }
}
