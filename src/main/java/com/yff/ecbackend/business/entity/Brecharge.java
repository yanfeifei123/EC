package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "b_recharge")
@org.hibernate.annotations.Table(appliesTo = "b_recharge",comment = "充值设置")
public class Brecharge extends BaseEntity<Long> {

    @Column(columnDefinition = "decimal(6,2) comment '金额'")
    private float money;

    @Column(columnDefinition = "int(255) comment '关联分店id'")
    private Long branchid;



}
