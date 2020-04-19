package com.yff.ecbackend.business.controller;


import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
import com.yff.ecbackend.business.service.BcategoryService;
import com.yff.ecbackend.business.service.BproductService;
import com.yff.ecbackend.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    private BbranchService bbranchService;
    @RequestMapping("/getgoods")
    @ResponseBody
    public Object getgoods(String businessid) {
        return businessService.getgoods(businessid);
    }


    @RequestMapping("/findbusinessAll")
    @ResponseBody
    public Object findbusinessAll(HttpServletRequest request, String businessid){

        return bcategoryService.findbusinessAll(request,Long.valueOf(businessid));
    }
    @RequestMapping("/findByproductPackage")
    @ResponseBody
    public Object findByproductPackage(HttpServletRequest request, String pid){
        return  bproductService.findByproductPackage(request,Long.valueOf(pid));
    }

    @RequestMapping("/findByBbranch")
    @ResponseBody
    public Object findByfirstmoney(String id){
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(id));
        return bbranch;
    }


}
