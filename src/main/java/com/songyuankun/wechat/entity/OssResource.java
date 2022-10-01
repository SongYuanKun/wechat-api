package com.songyuankun.wechat.entity;


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
@DynamicInsert
@DynamicUpdate
public class OssResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

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
