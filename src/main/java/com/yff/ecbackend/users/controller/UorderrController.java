package com.yff.ecbackend.users.controller;


import com.yff.ecbackend.users.service.UorderrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/uorderr")
public class UorderrController {

    @Autowired
    private UorderrService uorderrService;

    @RequestMapping(value = "/findUorderrToberefunded", method = RequestMethod.POST)
    @ResponseBody
    public Object findUorderrToberefunded(String orderid){
         return this.uorderrService.findUorderrOrderid(Long.valueOf(orderid));
    }

}
