package com.yff.ecbackend.business.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_category")
@org.hibernate.annotations.Table(appliesTo = "b_category",comment = "商品分类表")
public class Bcategory extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(255) comment '分类名称'")
    private String name;

    @Column(columnDefinition = "int(255) comment '关联商家id'")
    private Long businessid;

    @Column(columnDefinition = "int(255) comment '分类上级id自关联'")
    private Long pid;


    @Column(columnDefinition = "varchar(500) comment '备注'")
    private String note;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Long businessid) {
        this.businessid = businessid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
