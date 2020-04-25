package com.yff.ecbackend.users.controller;


import com.yff.ecbackend.users.service.UorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UorderService uorderService;

    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderList(HttpServletRequest request, String openid) {
//        System.out.println("openid:" + openid);
        return this.uorderService.findOrderList(request, openid);
    }

    /**
     * 点餐用户端订单明细
     *
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/findOrderDetailed", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderDetailed(HttpServletRequest request, String orderid) {
        return this.uorderService.findOrderDetailed(request, orderid);
    }


}
