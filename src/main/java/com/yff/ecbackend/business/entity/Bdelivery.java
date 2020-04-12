package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_delivery")
@org.hibernate.annotations.Table(appliesTo = "b_delivery",comment = "配送费包装费")
public class Bdelivery extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '绑定商品信息'")
    private Long categorysid;

    @Column(columnDefinition = "float comment '包装费（单个）'")
    private float bzcost;


    public Long getCategorysid() {
        return categorysid;
    }

    public void setCategorysid(Long categorysid) {
        this.categorysid = categorysid;
    }

    public float getBzcost() {
        return bzcost;
    }

    public void setBzcost(float bzcost) {
        this.bzcost = bzcost;
    }
}
