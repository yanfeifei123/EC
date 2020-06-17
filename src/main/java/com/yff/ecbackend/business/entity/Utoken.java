package com.yff.ecbackend.business.entity;

import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_token")
@org.hibernate.annotations.Table(appliesTo = "u_token",comment = "存储微信token")
public class Utoken  extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(500) comment 'token'")
    private String token;

    @Column(columnDefinition = "int(20) comment 'expiresin'")
    private int expiresin;

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
