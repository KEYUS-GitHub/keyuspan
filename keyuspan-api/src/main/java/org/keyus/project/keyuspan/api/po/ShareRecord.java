package org.keyus.project.keyuspan.api.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author keyus
 * @create 2019-08-05  下午1:57
 */
@Data
@Entity(name = "share_record")
public class ShareRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    // 存储通过逗号分隔的本次分享涉及的文件ID
    @Column(name = "files_ids")
    private String filesIds;

    // 存储通过逗号分隔的本次分享涉及的文件夹ID
    @Column(name = "folders_ids")
    private String foldersIds;

    // 验证码的值
    @Column(name = "cap_text")
    private String capText;

    // 分享连接失效的日期
    @Column(name = "date_of_invalid")
    private LocalDate dateOfInvalid;

    // 本次分享产生的页面URL
    @Column(name = "url")
    private String url;
}
