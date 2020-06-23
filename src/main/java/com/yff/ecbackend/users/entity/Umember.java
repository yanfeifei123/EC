package com.yff.ecbackend.users.entity;


import com.sun.xml.internal.rngom.parse.host.Base;
import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;
import org.aspectj.apache.bcel.generic.LineNumberGen;

import javax.persistence.*;


@Data
@Entity
@Table(name = "u_member")
@org.hibernate.annotations.Table(appliesTo = "u_member",comment = "会员当前金额")
public class Umember extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '关联用户id'")
    private Long userid;

    @Column(columnDefinition = "decimal(6,2) comment '金额'")
    private float money;

}
