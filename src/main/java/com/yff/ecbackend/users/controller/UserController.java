package com.yff.ecbackend.users.controller;


import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.service.UaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class UserController {


    @Autowired
    private UaddressService uaddressService;

    @PostMapping("/findByUaddress")
    @ResponseBody
    public Object findByUaddress(String openid) {

        return this.uaddressService.findByUaddress(openid);
    }

    @PostMapping("/updateUaddress")
    @ResponseBody
    public int updateUaddress(String u_address,String openid) {

        return this.uaddressService.updateUaddress(u_address,openid);
    }

    @PostMapping("/selectAddress")
    @ResponseBody
    public int  selectAddress(String id){
        return this.uaddressService.selectAddress(id);
    }

    @PostMapping("/delAddress")
    @ResponseBody
    public Object delAddress(String id,String openid){
        this.uaddressService.delete(Long.valueOf(id));
        return this.uaddressService.findByUaddress(openid);
    }

    @PostMapping("/findByOneUaddress")
    @ResponseBody
    public Object findByOneUaddress(String id){
        Uaddress uaddress = this.uaddressService.findOne(Long.valueOf(id));
        uaddress.setBuildtime(new Date());
        return this.uaddressService.update(uaddress);
    }

}
