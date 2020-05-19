package com.yff.ecbackend.users.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.view.OrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 点餐用户端/统一订单管理
 */
@Controller
@RequestMapping("/weChatPay")
public class OrderManagerController {

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UorderService uorderService;

    /**
     * 微信统一下单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/doUnifiedOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object doUnifiedOrder(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String tradeno = params.get("tradeno");
        String openid = params.get("openid");
        String total_fee = params.get("total_fee");
        String body = params.get("body");
        return weChatService.UnifiedOrder(tradeno, openid, total_fee, body, request);
    }

    /**
     * 支付成功后回调生成订单信息
     *
     * @return
     */

    @RequestMapping(value = "/updateUserOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object updateUserOrder(@RequestBody(required = false) OrderBean orderBean) {
//        System.out.println(JSON.toJSONString(orderBean.getShoppingCart()));
        return this.uorderService.updateUserOrder(orderBean);
    }


    @RequestMapping(value = "/updateUserOrderSelf", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserOrderSelf() {
        return 1;
    }

    /**
     * 获取订单商户号
     *
     * @return
     */
    @RequestMapping(value = "/createOutTradeno", method = RequestMethod.POST)
    @ResponseBody
    public String createOutTradeno() {
        return this.weChatService.createOutTradeno();
    }


    @JwtIgnore
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public void notify(@RequestBody HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付成功回调：notify");
    }


}
