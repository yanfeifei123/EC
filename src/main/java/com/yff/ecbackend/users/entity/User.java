package com.yff.ecbackend.users.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user",comment = "点餐用户/商家员工/老板")
public class User extends BaseEntity<Long> {



    @Column(columnDefinition = "varchar(255) comment '微信用户昵称'")
    private String nickname;


    @Column(columnDefinition = "int comment '性别'")
    private int gender;


    @Column(columnDefinition = "varchar(255) comment '用户头像'")
    private String avatarurl;


    @Column(columnDefinition = "varchar(50) comment '手机号码'")
    private String phone;

    @Column(columnDefinition = "varchar(50) comment '收货姓名'")
    private String name;

    @Column(columnDefinition = "varchar(250) comment '微信用户唯一标识'")
    private String openid;

    @Column(columnDefinition = "int(255) comment '关联商家'")
    private Long bid;

    @Column(columnDefinition = "int(255) comment '关联商家(分店)'")
    private Long  branchid;

    @Column(columnDefinition = "varchar(250) comment '账号'")
    private String  account;

    @Column(columnDefinition = "varchar(250) comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(20) DEFAULT '0' comment '是否会员(0否,1是)'")
    private String member;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Long getBranchid() {
        return branchid;
    }

    public void setBranchid(Long branchid) {
        this.branchid = branchid;
    }
}
