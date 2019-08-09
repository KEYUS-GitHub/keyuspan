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
public class VirtualFolderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<VirtualFolder> folders;

    private List<FileModel> files;

    // TODO: 19-8-6 对VO对象进行改造，隐藏敏感信息
    public static VirtualFolderVO getInstance(List<VirtualFolder> virtualFolders, List<FileModel> fileModels) {
        return new VirtualFolderVO(virtualFolders, fileModels);
    }
}
