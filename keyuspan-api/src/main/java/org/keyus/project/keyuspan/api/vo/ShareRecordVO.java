package org.keyus.project.keyuspan.api.vo;

import lombok.*;
import org.keyus.project.keyuspan.api.po.ShareRecord;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-09  下午10:28
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShareRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long memberId;

    private String capText;

    private LocalDate dateOfInvalid;

    private String url;

    public static ShareRecordVO getInstance(ShareRecord record) {
        return ShareRecordVO.builder().id(record.getId())
                .memberId(record.getMemberId())
                .capText(record.getCapText())
                .dateOfInvalid(record.getDateOfInvalid())
                .url(record.getUrl())
                .build();
    }

    public static List<ShareRecordVO> getInstances(List<ShareRecord> records) {
        List<ShareRecordVO> results = new ArrayList<>(records.size());
        for (ShareRecord record : records) {
            results.add(ShareRecordVO.getInstance(record));
        }
        return results;
    }
}
