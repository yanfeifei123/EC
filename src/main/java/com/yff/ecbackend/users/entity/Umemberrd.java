package com.yff.ecbackend.users.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "u_memberrd")
@org.hibernate.annotations.Table(appliesTo = "u_memberrd",comment = "充值记录")
public class Umemberrd extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) comment '关联用户id'")
    private Long userid;


    @Column(columnDefinition = "decimal(6,2) comment '充值金额'")
    private float money;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '生成商户单号'")
    private String tradeno;

}
