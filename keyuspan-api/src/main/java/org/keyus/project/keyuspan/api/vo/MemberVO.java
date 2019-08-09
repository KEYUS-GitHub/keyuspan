package org.keyus.project.keyuspan.api.vo;

import lombok.*;
import org.keyus.project.keyuspan.api.po.Member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-09  下午10:17
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private Integer memberType;

    private BigDecimal accountBalance;

    private LocalDate beginDate;

    private LocalDate endDate;

    private Double totalStorageSpace;

    private Double usedStorageSpace;

    private Integer status;

    private Integer integral;

    private Integer garbageCollectionDays;

    private String headImgUri;

    private Long mainFolderId;

    public static MemberVO getInstance(Member member) {
        return MemberVO.builder().id(member.getId())
                .username(member.getUsername())
                .memberType(member.getMemberType())
                .accountBalance(member.getAccountBalance())
                .beginDate(member.getBeginDate())
                .endDate(member.getEndDate())
                .totalStorageSpace(member.getTotalStorageSpace())
                .usedStorageSpace(member.getUsedStorageSpace())
                .status(member.getStatus())
                .integral(member.getIntegral())
                .garbageCollectionDays(member.getGarbageCollectionDays())
                .headImgUri(member.getHeadImgUri())
                .mainFolderId(member.getMainFolderId())
                .build();
    }

    public static List<MemberVO> getInstances(List<Member> members) {
        List<MemberVO> results = new ArrayList<>(members.size());
        for (Member member : members) {
            results.add(MemberVO.getInstance(member));
        }
        return results;
    }
}
