package com.yff.ecbackend.business.controller;


import com.yff.ecbackend.business.service.BcategoryService;
import com.yff.ecbackend.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private BcategoryService bcategoryService;

    @RequestMapping("/getgoods")
    @ResponseBody
    public Object getgoods(String businessid) {
        return businessService.getgoods(businessid);
    }


    @RequestMapping("/findbusinessAll")
    @ResponseBody
    public Object findbusinessAll(String businessid){
        return bcategoryService.findbusinessAll(Long.valueOf(businessid));
    }



}
