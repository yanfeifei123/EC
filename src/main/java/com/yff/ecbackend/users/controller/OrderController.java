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

    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderList(@RequestBody Map<String, String> params, HttpServletRequest request) {
//        System.out.println("pageNum:" + pageNum+"  pageSize:"+pageSize);
        String openid = params.get("openid");
        String pageNum = params.get("pageNum");
        String pageSize = params.get("pageSize");
        return this.uorderService.findOrderList(request, openid, pageNum, pageSize);
    }

    @RequestMapping(value = "/countAllByUorderOAndOpenid", method = RequestMethod.POST)
    @ResponseBody
    public Object countAllByUorderOAndOpenid(@RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String pageSize = params.get("pageSize");
        return this.uorderService.countAllByUorderOAndOpenid(openid, pageSize);
    }

    /**
     * 点餐用户端订单明细
     *
     * @return
     */
    @RequestMapping(value = "/findOrderDetailed", method = RequestMethod.POST)
    @ResponseBody
    public Object findOrderDetailed(@RequestBody  Map<String, String> params ,HttpServletRequest request ) {
        return this.uorderService.findOrderDetailed(request, params.get("orderid"));
    }


    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public int deleteOrder(@RequestBody Map<String, String> params ) {

        return this.uorderService.deleteOrder(params.get("orderid") );
    }

    @RequestMapping(value = "/queryOrderSettiing", method = RequestMethod.POST)
    @ResponseBody
    public Object queryOrderSettiing(@RequestBody Map<String, String> params  ) {
        String openid=params.get("openid");
        String branchid = params.get("branchid");
        return this.uorderService.queryOrderSettiing(openid, branchid);
    }

    @RequestMapping(value = "/clearMyorder", method = RequestMethod.POST)
    @ResponseBody
    public void clearMyorder(@RequestBody Map<String, String> params ) {
//        System.out.println("清除未支付的订单信息");
        this.uorderService.clearMyorder(params.get("openid"));
    }


}
