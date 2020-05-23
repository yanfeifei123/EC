package com.yff.ecbackend.business.controller;


import com.yff.ecbackend.business.service.BOrderService;
import com.yff.ecbackend.users.service.UorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 商家订单查询
 */

@Controller
@RequestMapping("/border")
public class BOrderController {

    @Autowired
    private BOrderService bOrderService;

    @Autowired
    private  UorderService uorderService;

    /**
     * 统计今天现在订单金额数量和退款金额findByOrderSummary
     *
     * @param branchid
     * @return
     */
    @RequestMapping(value = "/findByOrderSummary", method = RequestMethod.POST)
    @ResponseBody
    public Object findByOrderSummary(String branchid, String orderSummaryitem) {

        return this.bOrderService.findByOrderSummary(branchid, orderSummaryitem);
    }


    /**
     * 统计订单未完成数量
     *
     * @param branchid
     * @return
     */
    @RequestMapping(value = "/findByIsNotOrderComplete", method = RequestMethod.POST)
    @ResponseBody
    public Object findByIsNotOrderComplete(String branchid) {
        return this.bOrderService.findByIsNotOrderComplete(branchid);
    }

    @RequestMapping(value = "/findByBranchOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object findByBranchOrder(String branchid, String tabid,String pageNum,String pageSize) {
//        System.out.println("tabid:" + tabid);
        return this.bOrderService.findByBranchOrder(branchid,tabid,pageNum,pageSize);
    }

    /**
     * 完成订单
     * @param orderid
     * @return
     */
    @RequestMapping("/completeUorder")
    @ResponseBody
    public Object  completeUorder(String orderid){
       return this.uorderService.updateUorderComplete(orderid);
    }


}
