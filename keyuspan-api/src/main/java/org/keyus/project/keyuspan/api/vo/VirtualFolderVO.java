package org.keyus.project.keyuspan.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.VirtualFolder;

import java.io.Serializable;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-28  下午4:36
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VirtualFolderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<VirtualFolder> folders;

    private List<FileModel> files;

    public static VirtualFolderVO getInstance(List<VirtualFolder> virtualFolders, List<FileModel> fileModels) {
        return new VirtualFolderVO(virtualFolders, fileModels);
    }
}
