package com.yff.ecbackend.business.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bbluetooth;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@Controller
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private BcategoryService bcategoryService;

    @Autowired
    private BproductService bproductService;

    @Autowired
    private BbluetoothService bbluetoothService;

    @Autowired
    private BbranchService bbranchService;

    @RequestMapping("/getgoods")
    @ResponseBody
    public Object getgoods(String businessid) {
        return businessService.getgoods(businessid);
    }


    @RequestMapping("/findbusinessAll")
    @ResponseBody
    public Object findbusinessAll(HttpServletRequest request, String businessid) {

        return bcategoryService.findbusinessAll(request, Long.valueOf(businessid));
    }

    @RequestMapping("/findByproductPackage")
    @ResponseBody
    public Object findByproductPackage(HttpServletRequest request, String pid) {
        return bproductService.findByproductPackage(request, Long.valueOf(pid));
    }

    @RequestMapping("/findByBbranch")
    @ResponseBody
    public Object findByfirstmoney(String id) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(id));
        return bbranch;
    }

    /**
     * 商家营业和下班
     *
     * @param branchid
     * @param bornot
     * @return
     */
    @RequestMapping("/setbranchbornot")
    @ResponseBody
    public Object bornot(String branchid, String bornot) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(branchid));
        if (ToolUtil.isNotEmpty(bbranch)) {
            bbranch.setBornot(Integer.parseInt(bornot));
            this.bbranchService.update(bbranch);
        }
        return 1;
    }

    @RequestMapping("/findBybornot")
    @ResponseBody
    public Object findBybornot(String branchid) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(branchid));
        return bbranch;
    }

    @RequestMapping("/updateBbluetooth")
    @ResponseBody
    public Object updateBbluetooth(String bbluetooth) {
//        System.out.println("bbluetooth:" + bbluetooth);
        Bbluetooth o = JSON.parseObject(bbluetooth, Bbluetooth.class);
        return this.bbluetoothService.updatebbluetooth(o);

    }

    @RequestMapping("/findByBbluetooth")
    @ResponseBody
    public Object findByBbluetooth(String branchid) {
        return this.bbluetoothService.findByBbluetooth(branchid);
    }


}
