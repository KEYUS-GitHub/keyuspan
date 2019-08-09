package org.keyus.project.keyuspan.api.vo;

import lombok.*;
import org.keyus.project.keyuspan.api.po.FileModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-09  下午10:07
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FileModelVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String fileName;

    private String contentType;

    private Double size;

    private LocalDate updateDate;

    public static FileModelVO getInstance(FileModel fileModel) {
        return FileModelVO.builder().id(fileModel.getId())
                .fileName(fileModel.getFileName())
                .contentType(fileModel.getContentType())
                .size(fileModel.getSize())
                .updateDate(fileModel.getUpdateDate())
                .build();
    }

    public static List<FileModelVO> getInstances(List<FileModel> fileModels) {
        List<FileModelVO> results = new ArrayList<>(fileModels.size());
        for (FileModel fm : fileModels) {
            results.add(FileModelVO.getInstance(fm));
        }
        return results;
    }
}
