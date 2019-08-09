package org.keyus.project.keyuspan.api.vo;

import lombok.*;
import org.keyus.project.keyuspan.api.po.VirtualFolder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-09  下午10:37
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FolderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String virtualFolderName;

    private LocalDate updateDate;

    public static FolderVO getInstance(VirtualFolder folder) {
        return FolderVO.builder().id(folder.getId())
                .virtualFolderName(folder.getVirtualFolderName())
                .updateDate(folder.getUpdateDate())
                .build();
    }

    public static List<FolderVO> getInstances(List<VirtualFolder> folders) {
        List<FolderVO> results = new ArrayList<>(folders.size());
        for (VirtualFolder folder : folders) {
            results.add(FolderVO.getInstance(folder));
        }
        return results;
    }
}
