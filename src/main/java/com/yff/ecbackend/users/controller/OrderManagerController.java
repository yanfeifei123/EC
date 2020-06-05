package com.yff.ecbackend.users.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.service.MessagePushService;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.common.view.CommonReturnType;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.view.OrderBean;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;
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


    @Autowired
    private MessagePushService messagePushService;

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
        if (ToolUtil.isEmpty(orderObj)) {
            commonReturnType.setCode(-1);
            commonReturnType.setMsg("400");
        } else {
            OrderBean bean = JSON.parseObject(orderObj, OrderBean.class);
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


    /**
     * 付款成功后回调
     *
     * @param request
     * @param response
     */
    @JwtIgnore
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付成功回调：notify");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String notityXml = sb.toString();

            Map<String, String> map = WXPayUtil.xmlToMap(notityXml);
            String returnCode = map.get("return_code");
            String result_code = map.get("result_code");
            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(result_code)) {

                String transaction_id = map.get("transaction_id");//微信支付订单号
                String out_trade_no = map.get("out_trade_no");//商户订单号
                String openid = map.get("openid");
                String trade_type = map.get("trade_type");
                System.out.println("微信支付订单号:" + transaction_id + " 商户订单号:" + out_trade_no + " openid:" + openid + "  trade_type:" + trade_type);

                this.messagePushService.paymentCallbackMessage(out_trade_no);


            }
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款回调
     *
     * @param request
     * @param response
     */
    @JwtIgnore
    @RequestMapping(value = "/refundnotify", method = RequestMethod.POST)
    @ResponseBody
    public void refundnotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("退款成功回调：refundnotify");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String notityXml = sb.toString();
//            System.out.println(notityXml);

            Map<String, String> map = WXPayUtil.xmlToMap(notityXml);

            String returnCode = map.get("return_code");

            if ("SUCCESS".equals(returnCode)) {

                String req_info = map.get("req_info");
                this.messagePushService.refundnotify(req_info);

            }
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 退款申请
     * orderDetail
     *
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public Object refund(String out_trade_no, String total_fee) {
//        System.out.println("进入退款申请:"+out_trade_no+"   "+total_fee);
        return this.weChatService.refund(out_trade_no, total_fee);
    }


}
