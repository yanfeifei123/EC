package com.yff.ecbackend.sys.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_authorize")
@org.hibernate.annotations.Table(appliesTo = "sys_authorize",comment = "权限")
public class SysAuthorize extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) DEFAULT NULL COMMENT '关联用户'")
    private Long userid;

    @Column(columnDefinition = "int(255) DEFAULT NULL COMMENT '关联菜单'")
    private Long menuid;


}
