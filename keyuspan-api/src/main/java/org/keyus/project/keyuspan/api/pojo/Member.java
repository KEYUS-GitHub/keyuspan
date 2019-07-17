package org.keyus.project.keyuspan.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author keyus
 * @create 2019-07-16  上午11:04
 * 会员类（即本项目的用户，考虑到该项目无需设计得太复杂，本项目中
 * 会员分普通会员与超级会员，在本项目中用户与会员等价）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户名称
    @Column(name = "username")
    private String username;

    // 用户密码
    @Column(name = "password")
    private String password;

    // 会员类型
    @Column(name = "member_type")
    private Integer memberType;

    // 用户余额
    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    // 开启超级会员特权的起始日期
    @Column(name = "begin_date")
    private Date beginDate;

    // 超级会员特权的结束日期
    @Column(name = "end_date")
    private Date endDate;

    // 可使用的存储空间总量（单位为MB）
    @Column(name = "total_storage_space")
    private Double totalStorageSpace;

    // 已经使用的存储空间（单位为MB）
    @Column(name = "used_storage_space")
    private Double usedStorageSpace;

    // 本用户的状态，如：正常、冻结、封禁等等，为防止以后
    // 需要记录多种状态，使用整型而不是布尔型来存储
    @Column(name = "status")
    private Integer status;

    // 用户积分
    @Column(name = "integral")
    private Integer integral;

    // 回收站有效期（即垃圾文件放入回收站后，经过多长时间回收，以天为单位）
    @Column(name = "garbage_collection_days")
    private Integer garbageCollectionDays;

    // 转存文件数上线
    @Column(name = "max_files_transfer_count")
    private Integer maxFilesTransferCount;

    // 单次文件上传时，单个大文件上传时的大小限制（单位为MB）
    @Column(name = "simple_big_file_upload_max_size")
    private Double simpleBigFileUploadMaxSize;

    // 头像图片的URI
    @Column(name = "head_img_uri")
    private String headImgUri;
}