package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "b_categorys")
@org.hibernate.annotations.Table(appliesTo = "b_categorys",comment = "商品表")
public class Bcategorys extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '关联商家id'")
    private Long businessid;


    @Column(columnDefinition = "int(255) comment '关联分类id'")
    private Long categoryid;

    @Column(columnDefinition = "varchar(255) comment '商品名称'")
    private String name;

    @Column(columnDefinition = "decimal comment '现价'")
    private float currentprice;


    @Column(columnDefinition = "decimal comment '原价'")
    private float unitprice;


    @Column(columnDefinition = "varchar(50) comment '折扣'")
    private float discount;

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

    public float getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(float currentprice) {
        this.currentprice = currentprice;
    }

    public float getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(float unitprice) {
        this.unitprice = unitprice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
