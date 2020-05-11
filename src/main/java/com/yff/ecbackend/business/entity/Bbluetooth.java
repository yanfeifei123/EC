package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_bluetooth")
@org.hibernate.annotations.Table(appliesTo = "b_bluetooth",comment = "蓝牙小票机设置")
public class Bbluetooth extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255)   comment '分店id'")
    private Long branchid;

    @Column(columnDefinition = "int(255)   comment '操作用戶'")
    private Long userid;

    @Column(columnDefinition = "varchar(255)   comment '设备'")
    private String deviceId;

    @Column(columnDefinition = "varchar(255)   comment '服务id'")
    private String serviceId;

    @Column(columnDefinition = "varchar(255)   comment '设备入口id'")
    private String characteristicId;


    public Long getBranchid() {
        return branchid;
    }

    public void setBranchid(Long branchid) {
        this.branchid = branchid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(String characteristicId) {
        this.characteristicId = characteristicId;
    }
}
