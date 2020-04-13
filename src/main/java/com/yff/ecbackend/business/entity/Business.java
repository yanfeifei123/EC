package com.yff.ecbackend.business.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_business")
@org.hibernate.annotations.Table(appliesTo = "b_business",comment = "商家信息表")
public class Business extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(150) comment '商家名称'")
    private String name;


    @Column(columnDefinition = "varchar(50) comment '联系电话'")
    private String phone;

    @Column(columnDefinition = "varchar(150) comment '支付微信账号'")
    private String wxzfzh;

    @Column(columnDefinition = "varchar(150) comment '银行卡号'")
    private String bankcard;


    @Column(columnDefinition = "varchar(220) comment '备注'")
    private String note;

    @Column(columnDefinition = "decimal comment '配送费'")
    private float psfcost;

    @Column(columnDefinition = "decimal comment '首单用户-x元'")
    private float firstorder;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWxzfzh() {
        return wxzfzh;
    }

    public void setWxzfzh(String wxzfzh) {
        this.wxzfzh = wxzfzh;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public float getPsfcost() {
        return psfcost;
    }

    public void setPsfcost(float psfcost) {
        this.psfcost = psfcost;
    }

    public float getFirstorder() {
        return firstorder;
    }

    public void setFirstorder(float firstorder) {
        this.firstorder = firstorder;
    }
}
