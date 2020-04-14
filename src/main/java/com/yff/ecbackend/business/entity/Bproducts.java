package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "b_product")
@org.hibernate.annotations.Table(appliesTo = "b_product",comment = "商品表")
public class Bproducts extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '关联商家id'")
    private Long businessid;


    @Column(columnDefinition = "int(255) comment '关联分类id'")
    private Long categoryid;

    @Column(columnDefinition = "varchar(255) comment '商品名称'")
    private String name;

    @Column(columnDefinition = "decimal(6,2) comment '单价'")
    private float price;


    @Column(columnDefinition = "decimal(6,2) comment '会员价'")
    private float memberprice;


    @Column(columnDefinition = "varchar(255) comment '备注'")
    private String note;

    @Column(columnDefinition = "int(255) comment '关联上级id（组合套餐）'")
    private Long pid;


    public Long getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Long businessid) {
        this.businessid = businessid;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(float memberprice) {
        this.memberprice = memberprice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
