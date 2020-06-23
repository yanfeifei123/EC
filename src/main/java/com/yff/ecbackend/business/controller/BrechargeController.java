package com.yff.ecbackend.business.controller;

import com.alibaba.fastjson.JSON;
import com.yff.ecbackend.business.entity.Brecharge;
import com.yff.ecbackend.business.service.BrechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/brecharge")
public class BrechargeController {

    @Autowired
    private BrechargeService brechargeService;

    @RequestMapping("/findBybrecharge")
    @ResponseBody
    public Object findBybrecharge(String branchid){
       return this.brechargeService.findBybrecharge(Long.valueOf(branchid));
    }


    @RequestMapping("/updatabrecharge")
    @ResponseBody
    public Object updatabrecharge(String brecharge){
        Brecharge br =  JSON.parseObject(brecharge, Brecharge.class);
        return this.brechargeService.updatabrecharge(br);
    }

    @RequestMapping("/delbrecharge")
    @ResponseBody
    public Object delbrecharge(String id){
        Brecharge brecharge = this.brechargeService.findOne(Long.valueOf(id));
        this.brechargeService.delete(Long.valueOf(id));
        return this.brechargeService.findBybrecharge(brecharge.getBranchid());
    }


}
