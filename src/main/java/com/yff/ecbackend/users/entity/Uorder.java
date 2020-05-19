package com.yff.ecbackend.users.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.yff.core.jparepository.entity.BaseEntity;
import com.yff.ecbackend.users.view.OrderItem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "u_order")
@org.hibernate.annotations.Table(appliesTo = "u_order", comment = "用户订单信息")
public class Uorder extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '关联用户id'")
    private Long userid;


    @Column(columnDefinition = "varchar(250) comment '微信用户唯一标识'")
    private String openid;


    @Column(columnDefinition = "decimal(6,2) comment '订单总金额'")
    private float totalfee;

    @Column(columnDefinition = "decimal(6,2) comment '优惠金额'")
    private float discount;

    @Column(columnDefinition = "int(255) comment '商家id'")
    private Long bid;

    @Column(columnDefinition = "int(255) comment '分店id'")
    private Long branchid;


    @Column(columnDefinition = "int comment '状态(0未支付，1已支付)'")
    private int status;

    @Column(columnDefinition = "int DEFAULT '0' comment '是否完成(0未完成，1已完成)'")
    private int iscomplete;

    @Column(columnDefinition = "int comment '是否到店自取(0否，1是，2已取消)'")
    private int self;

    @Column(columnDefinition = "varchar(5000) comment '备注'")
    private String note;

    @JSONField(serialize = false)
    @Column(columnDefinition = "text  comment '存储json格式字符串'")
    private String json;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '订单完成时间'")
    private Date completetime;

    @Column(columnDefinition = "varchar(50) comment '生成商户单号'")
    private String tradeno;

    @Column(columnDefinition = "bigint(20) comment '关联送餐地址id'")
    private Long uaddressid;

    @Column(columnDefinition = "decimal(6,2) comment '首单用户-xx元'")
    private float firstorder;

    @Column(columnDefinition = "int DEFAULT '0'  comment '是否是会员价购买 0,1'")
    private int ismember;


    @Column(columnDefinition = "varchar(225)   comment '收货人地址'")
    private String address;

    @Column(columnDefinition = "varchar(225)   comment '收货人姓名'")
    private String receiver;

    @Column(columnDefinition = "varchar(100)   comment '收货人电话'")
    private String phone;

    @Transient
    private String branchname;

    @Transient
    private List<OrderItem> orderItems = new ArrayList<>();

    @Transient
    private String info;

    @Transient
    private int total;


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


    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(int iscomplete) {
        this.iscomplete = iscomplete;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
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


    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 分店名称
     */
    public String getBranchname() {
        return branchname;
    }

    /**
     * 分店名称
     */
    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

    public Long getUaddressid() {
        return uaddressid;
    }

    public void setUaddressid(Long uaddressid) {
        this.uaddressid = uaddressid;
    }

    public float getFirstorder() {
        return firstorder;
    }

    public void setFirstorder(float firstorder) {
        this.firstorder = firstorder;
    }

    public int getIsmember() {
        return ismember;
    }

    public void setIsmember(int ismember) {
        this.ismember = ismember;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
