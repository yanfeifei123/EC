package com.yff.ecbackend.sys.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_menu")
@org.hibernate.annotations.Table(appliesTo = "sys_menu",comment = "系统菜单")
public class SysMenu extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) DEFAULT NULL COMMENT '关联上级id（组合套餐）'")
    private Long pid;

    @Column(columnDefinition = "varchar(250) comment '菜单名称'")
    private String name;

    @Column(columnDefinition = "varchar(250) comment '菜单图标'")
    private String icon;

    @Column(columnDefinition = "varchar(250) comment '页面路径'")
    private String url;

    @Column(columnDefinition = "varchar(250) comment '菜单,按钮'")
    private String type;



}
