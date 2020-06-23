package com.yff.ecbackend.common.controller;


import com.yff.core.util.ToolUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 接收请求测试
 */
@Controller
public class Testcontroller {
    /**
     * JSON提交方式
     * @param requestBody
     * @return
     */
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

    /**
     * post表单提交方式
     * @param orderid
     * @param tradeno
     * @param shoppingcart
     * @param openid
     * @param businessid
     * @param branchid
     * @param isself
     * @param discount
     * @param totalprice
     * @param uaddressid
     * @param firstorder
     * @param ismember
     * @return
     */
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
