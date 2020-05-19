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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
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

    @RequestMapping("/getgoods")
    @ResponseBody
    public Object getgoods(@RequestBody Map<String,String> params) {

        return businessService.getgoods(params.get("businessid"));
    }


    @RequestMapping("/findbusinessAll")
    @ResponseBody
    public Object findbusinessAll(@RequestBody  Map<String,String> params  ,  HttpServletRequest request  ) {

        return bcategoryService.findbusinessAll(request, Long.valueOf(params.get("businessid")));
    }

    @RequestMapping("/findByproductPackage")
    @ResponseBody
    public Object findByproductPackage(@RequestBody Map<String, String> params , HttpServletRequest request ) {
        return bproductService.findByproductPackage(request, Long.valueOf(params.get("pid")));
    }

    @RequestMapping("/findByBbranch")
    @ResponseBody
    public Object findByfirstmoney(@RequestBody Map<String,String> params  ) {

        Bbranch bbranch = bbranchService.findOne(Long.valueOf(params.get("id")));
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
    public Object bornot(@RequestBody Map<String, String> params) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(params.get("branchid")));
        if (ToolUtil.isNotEmpty(bbranch)) {
            bbranch.setBornot(Integer.parseInt(params.get("bornot") ));
            this.bbranchService.update(bbranch);
        }
        return 1;
    }

    @RequestMapping("/findBybornot")
    @ResponseBody
    public Object findBybornot(@RequestBody Map<String, String> params) {
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(params.get("branchid") ));
        return bbranch;
    }

    @RequestMapping("/updateBbluetooth")
    @ResponseBody
    public Object updateBbluetooth(@RequestBody  Map<String, String> params) {
//        System.out.println("bbluetooth:" + bbluetooth);
        Bbluetooth o = JSON.parseObject(params.get("bbluetooth") , Bbluetooth.class);
        return this.bbluetoothService.updatebbluetooth(o);
    }

    @RequestMapping("/findByBbluetooth")
    @ResponseBody
    public Object findByBbluetooth(@RequestBody Map<String, String> params) {
        return this.bbluetoothService.findByBbluetooth(params.get("branchid") );
    }


}
