package com.yff.ecbackend.users.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "u_orderr")
@org.hibernate.annotations.Table(appliesTo = "u_orderr", comment = "订单退款描述")
public class Uorderr  extends BaseEntity<Long> {


    @Column(columnDefinition = "int(255) comment '关联订单ID'")
    private Long orderid;

    @Column(columnDefinition = "varchar(250) comment '微信用户唯一标识'")
    private String openid;

    @Column(columnDefinition = "int(255) comment '关联用户id'")
    private Long userid;

    @Column(columnDefinition = "text comment '照片路径'")
    private String url;

    @Column(columnDefinition = "varchar(500) comment '退款原因'")
    private String reason;

    @Column(columnDefinition = "int(11) DEFAULT '0' comment '是否同意退款(0否,1是)'")
    private int agree;

    @Column(columnDefinition = "int(11) DEFAULT '0'  comment '是否结束(0否,1是)'")
    private int end;


    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
