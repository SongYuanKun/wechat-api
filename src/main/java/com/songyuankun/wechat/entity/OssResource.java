package com.songyuankun.wechat.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 云存储资源表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "oss_resource")
@ToString
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "OssResource对象", description = "云存储资源表")
@DynamicInsert
@DynamicUpdate
public class OssResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    @Column(name = "file_name")
    private String fileName;

    private String url;

    @Column(name = "file_key")
    private String fileKey;

    public OssResource(String fileName, String url, String fileKey) {
        this.fileName = fileName;
        this.url = url;
        this.fileKey = fileKey;
    }
}
