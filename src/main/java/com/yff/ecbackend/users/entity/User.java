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
    private String nickName;


    @Column(columnDefinition = "int comment '性别'")
    private int gender;


    @Column(columnDefinition = "varchar(255) comment '用户头像'")
    private String avatarUrl;


    @Column(columnDefinition = "varchar(50) comment '手机号码'")
    private String phone;

    @Column(columnDefinition = "varchar(50) comment '收货姓名'")
    private String name;

    @Column(columnDefinition = "varchar(250) comment '微信用户唯一标识'")
    private String openid;

    @Column(columnDefinition = "int(255) comment '关联商家'")
    private Long bid;

    @Column(columnDefinition = "varchar(250) comment '账号'")
    private String  account;

    @Column(columnDefinition = "varchar(250) comment '密码'")
    private String password;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
