package com.yff.ecbackend.common.controller;



import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.ecbackend.common.service.MessagePushService;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;


@Controller
@RequestMapping("/pay")
public class PaymentcallbackController {

    @Autowired
    private MessagePushService messagePushService;

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


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
