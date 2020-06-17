package com.yff.ecbackend.business.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bbluetooth;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.entity.Bcategory;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.service.*;
import com.yff.ecbackend.common.view.CommonReturnType;
import com.yff.sysaop.SysLoga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private BphotoService bphotoService;

    @RequestMapping("/getgoods")
    @ResponseBody
    public Object getgoods(String businessid) {
        return businessService.getgoods(businessid);
    }



    @RequestMapping("/findbusinessAll")
    @ResponseBody
    public Object findbusinessAll(String branchid,String searchValue) {

        return bcategoryService.findbusinessAll(Long.valueOf(branchid),searchValue);
    }

    @RequestMapping("/findByproductPackage")
    @ResponseBody
    public Object findByproductPackage(HttpServletRequest request, String pid) {
        return bproductService.findByproductPackage(Long.valueOf(pid));
    }

    @RequestMapping("/findByBbranch")
    @ResponseBody
    public Object findByfirstmoney(String id) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(id));
//        String s = JSON.toJSONString(bbranch);
//        System.out.println(s);
        return bbranch;
    }

    @RequestMapping("/updateBbranch")
    @ResponseBody
    public Object updateBbranch(String bbranch) {
//        System.out.println(bbranch);
        return this.bbranchService.updateBbranch(bbranch);
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


    @RequestMapping("/findByBcategory")
    @ResponseBody
    public Object findByBcategory(String branchid,String searchValue) {
        return this.bcategoryService.findByBcategory(branchid,searchValue);
    }

    @RequestMapping("/findByBproduct")
    @ResponseBody
    public Object findByBproduct(HttpServletRequest request, String categoryid,String searchValue) {
        List<Bproduct> bproducts = this.bproductService.findinSetmealBproducts(Long.valueOf(categoryid),searchValue);
        this.bphotoService.setImagepath(bproducts);
        return bproducts;
    }

    @RequestMapping("/updatebcategory")
    @ResponseBody
    public Object updatebcategory(String bcategory) {
//        System.out.println(bcategory);
        CommonReturnType commonReturnType = new CommonReturnType();
        if (ToolUtil.isNotEmpty(bcategory)) {
            commonReturnType.setCode(1);
            commonReturnType.setMsg("ok");
            Bcategory bcategorypojo = JSON.parseObject(bcategory, Bcategory.class);
            Map<String, Object> map = (Map<String, Object>) this.bcategoryService.updatebcategory(bcategorypojo);
            commonReturnType.setData(map);

        } else {
            commonReturnType.setCode(-1);
            commonReturnType.setMsg("err");
        }
        return commonReturnType;
    }

    @RequestMapping("/deletebcategory")
    @ResponseBody
    public int deletebcategory(String bcategoryid) {
        return this.bcategoryService.deletebcategory(bcategoryid);
    }


}
