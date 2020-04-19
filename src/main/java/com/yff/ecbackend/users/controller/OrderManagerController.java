package com.yff.ecbackend.users.controller;


import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.service.UorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

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
    public Object doUnifiedOrder(String openid, String total_fee, String body, HttpServletRequest request) {
         return weChatService.UnifiedOrder(openid,total_fee,body,request);
    }

    /**
     * 支付成功后回调生成订单信息
     * @param openid
     * @param shoppingcart
     * @return
     */
    @RequestMapping(value = "/updateUserOrder", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserOrder(String openid,String shoppingcart,String bid,String totalfee, String branchid,String isself,String discount){
        return   this.uorderService.updateUserOrder(openid,shoppingcart,bid,totalfee,branchid,isself,discount);
    }

}
