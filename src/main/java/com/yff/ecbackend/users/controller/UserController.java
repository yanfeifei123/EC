package com.yff.ecbackend.users.controller;


import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.service.UaddressService;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.* ;


@Controller
public class UserController {


    @Autowired
    private UaddressService uaddressService;
    @Autowired
    private UserService userService;

    @PostMapping("/findByUaddress")
    @ResponseBody
    public Object findByUaddress(@RequestBody Map<String, String> params  ) {

        return this.uaddressService.findByUaddress(params.get("openid") );
    }

    @PostMapping("/updateUaddress")
    @ResponseBody
    public int updateUaddress(@RequestBody Map<String, String> params ) {
        String u_address=params.get("u_address");
        String openid=params.get("openid");
        return this.uaddressService.updateUaddress(u_address,openid);
    }

    @PostMapping("/selectAddress")
    @ResponseBody
    public int  selectAddress(Map<String, String> params  ){
        return this.uaddressService.selectAddress(params.get("id"));
    }

    @PostMapping("/delAddress")
    @ResponseBody
    public Object delAddress(@RequestBody Map<String, String> params ){
        String id = params.get("id");
        String openid = params.get("openid");
        this.uaddressService.delete(Long.valueOf(id));
        return this.uaddressService.findByUaddress(openid);
    }

    @PostMapping("/findByOneUaddress")
    @ResponseBody
    public Object findByOneUaddress(@RequestBody Map<String, String> params ){

        Uaddress uaddress = this.uaddressService.findOne(Long.valueOf(params.get("id")));
        uaddress.setBuildtime(new Date());
        return this.uaddressService.update(uaddress);
    }


    @PostMapping("/onisfirstorder")
    @ResponseBody
    public Object onisfirstorder(@RequestBody Map<String, String> params ){
        String branchid=params.get("branchid");
        String openid=params.get("openid");
        return  this.userService.onisfirstorder(branchid,openid);
    }


    @PostMapping("/findByUserid")
    @ResponseBody
    public Object findByUserid(@RequestBody Map<String, String> params){
        return  this.userService.findByUserid(params.get("openid"));
    }



}
