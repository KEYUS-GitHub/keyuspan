package org.keyus.project.keyuspan.api.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author keyus
 * @create 2019-07-22  下午4:04
 */
@Data
@Entity(name = "file_model")
public class FileModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 会员的ID
    @Column(name = "member_id")
    private Long memberId;

    // 文件名（包含扩展名）
    @Column(name = "file_name")
    private String fileName;

    // 文件扩展名（含.）
    @Column(name = "file_extension")
    private String fileExtension;

    // 文件保存在文件服务器时使用的uri
    @Column(name = "uri")
    private String uri;

    // 文件的类型（图片或视频等......）
    @Column(name = "file_type")
    private Integer fileType;

    // 文件的大小（单位是MB）
    @Column(name = "size")
    private Double size;

    // 修改日期
    @Column(name = "update_date")
    private Date updateDate;

    // 文件在进入回收站时，在哪个日期被删除
    @Column(name = "date_of_recovery")
    private Date dateOfRecovery;

    // 是否被删除
    @Column(name = "deleted")
    private Boolean deleted;

}
