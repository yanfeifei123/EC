package com.yff.ecbackend.users.controller;


import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UordertailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UorderService uorderService;
    @Autowired
    private UordertailService uordertailService;

    /**
     * 查询用户端订单列表
     * @param request
     * @param openid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderList( HttpServletRequest request,String openid,String pageNum,String pageSize) {
//        System.out.println("pageNum:" + pageNum+"  pageSize:"+pageSize);

        return this.uorderService.findOrderList(openid, pageNum, pageSize);
    }

    @RequestMapping(value = "/countAllByUorderOAndOpenid", method = RequestMethod.POST)
    @ResponseBody
    public Object countAllByUorderOAndOpenid(String  openid,String pageSize) {

        return this.uorderService.countAllByUorderOAndOpenid(openid, pageSize);
    }

    /**
     * 点餐用户端订单明细
     *
     * @return
     */
    @RequestMapping(value = "/findOrderDetailed", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderDetailed(String orderid) {
        return this.uorderService.findOrderDetailed(orderid);
    }


    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public int deleteOrder(String orderid) {

        return this.uorderService.deleteOrder(orderid );
    }

    @RequestMapping(value = "/queryOrderSettiing", method = RequestMethod.POST)
    @ResponseBody
    public Object queryOrderSettiing(String openid, String branchid) {

        return this.uorderService.queryOrderSettiing(openid, branchid);
    }

    @RequestMapping(value = "/clearMyorder", method = RequestMethod.POST)
    @ResponseBody
    public void clearMyorder( String openid ) {
//        System.out.println("清除未支付的订单信息");
        this.uorderService.clearMyorder(openid);
    }


}
