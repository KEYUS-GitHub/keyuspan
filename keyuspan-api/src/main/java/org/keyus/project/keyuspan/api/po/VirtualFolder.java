package org.keyus.project.keyuspan.api.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author keyus
 * @create 2019-07-27  下午10:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "virtual_folder")
public class VirtualFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 会员的ID
    @Column(name = "member_id")
    private Long memberId;

    // 该虚拟文件夹所在的父虚拟文件夹目录的ID
    @Column(name = "father_folder_id")
    private Long fatherFolderId;

    // 虚拟文件夹的名称
    @Column(name = "virtual_folder_name")
    private String virtualFolderName;

    // 修改日期
    @Column(name = "update_date")
    private LocalDate updateDate;

    // 是否被删除
    @Column(name = "deleted")
    private Boolean deleted;

    // 文件在进入回收站时，在哪个日期被删除
    @Column(name = "date_of_recovery")
    private LocalDate dateOfRecovery;
}
