package com.yff.ecbackend.users.controller;


import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.service.UorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * @param openid
     * @param total_fee
     * @param body
     * @param request
     * @return
     */
    @RequestMapping(value = "/doUnifiedOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object doUnifiedOrder(String tradeno, String openid, String total_fee, String body, HttpServletRequest request) {

        return weChatService.UnifiedOrder(tradeno,openid,total_fee,body,request);
    }

    /**
     * 支付成功后回调生成订单信息
     * @param openid
     * @param shoppingcart
     * @return
     */
    @RequestMapping(value = "/updateUserOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserOrder( String orderid,String tradeno, String openid,String shoppingcart,String bid,String totalfee, String branchid,String isself,String discount,String uaddressid,String firstorder,String ismember){
        return  this.uorderService.updateUserOrder(orderid,tradeno, openid,shoppingcart,bid,totalfee,branchid,isself,discount,uaddressid,firstorder,ismember);
    }


    @RequestMapping(value = "/updateUserOrderSelf", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserOrderSelf(){
        return 1;
    }

    /**
     * 获取订单商户号
     * @return
     */
    @RequestMapping(value = "/createOutTradeno", method = RequestMethod.POST)
    @ResponseBody
    public String createOutTradeno(){
        return this.weChatService.createOutTradeno();
    }


    @JwtIgnore
    @RequestMapping(value="/notify" ,  method = RequestMethod.POST)
    @ResponseBody
    public void notify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("支付成功回调：notify");
    }



}
