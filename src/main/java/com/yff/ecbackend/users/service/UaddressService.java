package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UaddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UaddressService extends BaseService<Uaddress,Long> {

    @Autowired
    private UaddressRepository uaddressRepository;

    @Autowired
    private UserService userService;



    public List<Uaddress> findByUaddress(String openid){
         User user = this.userService.findByUserid(openid);
         if(ToolUtil.isNotEmpty(user)){
             return this.uaddressRepository.findByUaddress(user.getId());
         }
         return null;
    }

    public List<Uaddress> findByUaddress(Long userid){
        return this.uaddressRepository.findByUaddress(userid);
    }


    public int updateUaddress(String u_address,String openid){
        Uaddress  uaddress   = JSON.parseObject(u_address,Uaddress.class);
        User user = this.userService.findByUserid(openid);
        uaddress.setUserid(user.getId());
//        uaddress.setBuildtime(new Date());
        this.update(uaddress);
        return 1;
    }

    public int saveUaddress(String u_address,String openid){
        Uaddress  uaddress   = JSON.parseObject(u_address,Uaddress.class);
        User user = this.userService.findByUserid(openid);
        uaddress.setUserid(user.getId());
        uaddress.setBuildtime(new Date());
        this.update(uaddress);
        return 1;
    }


    public Uaddress selectAddress(String id){

        Uaddress uaddress = this.findOne(Long.valueOf(id));
        if(ToolUtil.isNotEmpty(uaddress)){
            uaddress.setBuildtime(new Date());
            this.update(uaddress);
        }
        return uaddress;
    }


}
