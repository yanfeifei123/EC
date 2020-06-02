package com.yff.ecbackend.users.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.common.view.CommonReturnType;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.view.OrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    public Object doUnifiedOrder(HttpServletRequest request, String tradeno, String openid, String total_fee, String body) {
//        System.out.println("tradeno:"+tradeno);
        return weChatService.UnifiedOrder(tradeno, openid, total_fee, body, request);
    }

    /**
     * 支付成功后回调生成订单信息
     *
     * @return
     */
    @RequestMapping(value = "/updateUserOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserOrder(String orderObj) {
//        System.out.println("订单数据："+orderObj);
        CommonReturnType commonReturnType = new CommonReturnType();
        if(ToolUtil.isEmpty(orderObj)){
            commonReturnType.setCode(-1);
            commonReturnType.setMsg("400");
        }else{
            OrderBean  bean=  JSON.parseObject(orderObj,OrderBean.class);
            Object object = this.uorderService.updateUserOrder(bean);
            commonReturnType.setData(object);
        }
        return commonReturnType;
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


}
