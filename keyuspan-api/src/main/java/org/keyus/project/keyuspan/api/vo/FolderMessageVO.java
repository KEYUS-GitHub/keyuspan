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

    private List<FolderVO> folders;

    private List<FileModelVO> files;

    public static FolderMessageVO getInstance(List<VirtualFolder> virtualFolders, List<FileModel> fileModels) {
        return new FolderMessageVO(FolderVO.getInstances(virtualFolders), FileModelVO.getInstances(fileModels));
    }
}
