package com.yff.ecbackend.sys.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_userlog")
@org.hibernate.annotations.Table(appliesTo = "sys_userlog",comment = "系统日志访问情况")
public class SysUserLog extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(250) comment '用户标识'")
    private String username;

    @Column(columnDefinition = "varchar(250) comment '系统模块名称'")
    private String modulename;

    @Column(columnDefinition = "TEXT comment '请求参数'")
    private String inputp;

    @Column(columnDefinition = "TEXT comment '返回参数'")
    private String outputp;
}
