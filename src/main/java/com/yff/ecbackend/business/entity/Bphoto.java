package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "b_photo")
@org.hibernate.annotations.Table(appliesTo = "b_photo",comment = "商品照片")
public class Bphoto extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '操作者id'")
    private Long userid;

    @Column(columnDefinition = "int(255) comment '关联id'")
    private Long fkid;

    @Column(columnDefinition = "varchar(520) comment '路径'")
    private String path;

    @Column(columnDefinition = "float comment '文件大小'")
    private float size;


    @Column(columnDefinition = "varchar(520) comment '文件名称'")
    private String name;

    @Column(columnDefinition = "varchar(50) comment '文件后缀'")
    private String suffix;


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getFkid() {
        return fkid;
    }

    public void setFkid(Long fkid) {
        this.fkid = fkid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
