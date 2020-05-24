package com.yff.ecbackend.common.controller;


import com.yff.core.util.ToolUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Testcontroller {


    @PostMapping(value = "/testJosn", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object testJosn(@RequestBody String requestBody) {
        System.out.println("requestBody:" + requestBody);
        if (ToolUtil.isEmpty(requestBody)) {
            return "FAIL";
        } else {
            return "SUCCESS";
        }
    }

    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    @ResponseBody
    public Object testPost(String orderid,
                           String tradeno,
                           String shoppingcart,
                           String openid,
                           String businessid,
                           String branchid,
                           String isself,
                           String discount,
                           String totalprice,
                           String uaddressid,
                           String firstorder,
                           String ismember) {
        System.out.println("shoppingcart:"+shoppingcart+" openid:"+openid);
        return "";
    }


}
