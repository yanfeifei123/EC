package com.yff.ecbackend.users.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_order")
@org.hibernate.annotations.Table(appliesTo = "u_order",comment = "用户订单信息")
public class Uorder extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) comment '关联用户id'")
    private Long userid;


    @Column(columnDefinition = "varchar(250) comment '微信用户唯一标识'")
    private String openid;


    @Column(columnDefinition = "decimal(6,2) comment '订单总金额'")
    private float totalfee;

    @Column(columnDefinition = "varchar(5000) comment '订单主题'")
    private String body;

    @Column(columnDefinition = "int(255) comment '商家id'")
    private Long bid;

    @Column(columnDefinition = "int(255) comment '分店id'")
    private Long branchid;


    @Column(columnDefinition = "varchar(30) comment '状态(0未支付，1已支付)'")
    private String status;

    @Column(columnDefinition = "varchar(30) comment '是否完成(0未完成，1已完成)'")
    private String iscomplete;

    @Column(columnDefinition = "varchar(30) comment '是否到店自取(0否，1是)'")
    private String self;

    @Column(columnDefinition = "varchar(5000) comment '备注'")
    private String note;


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public float getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(float totalfee) {
        this.totalfee = totalfee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(String iscomplete) {
        this.iscomplete = iscomplete;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getBranchid() {
        return branchid;
    }

    public void setBranchid(Long branchid) {
        this.branchid = branchid;
    }
}
