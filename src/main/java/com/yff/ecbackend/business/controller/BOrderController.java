package com.yff.ecbackend.business.controller;


import com.yff.core.util.DateUtil;
import com.yff.ecbackend.business.service.BOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 商家订单查询
 */

@Controller
@RequestMapping("/border")
public class BOrderController {

    @Autowired
    private BOrderService bOrderService;

    /**
     * 统计今天现在订单金额数量和退款金额findByOrderSummary
     * @param branchid
     * @return
     */
    @RequestMapping(value = "/findByOrderSummary", method = RequestMethod.POST)
    @ResponseBody
    public Object findByOrderSummary(String branchid,String orderSummaryitem){

        return this.bOrderService.findByOrderSummary(branchid,orderSummaryitem);
    }





}