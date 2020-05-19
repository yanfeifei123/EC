package com.yff.ecbackend.business.controller;


import com.yff.ecbackend.business.service.BOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     *
     * @return
     */
    @RequestMapping(value = "/findByOrderSummary", method = RequestMethod.POST)
    @ResponseBody
    public Object findByOrderSummary(@RequestBody Map<String, String> params) {

        return this.bOrderService.findByOrderSummary(params.get("branchid") , params.get("orderSummaryitem") );
    }


    /**
     * 统计订单未完成数量
     *
     * @return
     */
    @RequestMapping(value = "/findByIsNotOrderComplete", method = RequestMethod.POST)
    @ResponseBody
    public Object findByIsNotOrderComplete(@RequestBody Map<String, String> params) {
        return this.bOrderService.findByIsNotOrderComplete(params.get("branchid"));
    }

    @RequestMapping(value = "/findByBranchOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object findByBranchOrder(@RequestBody Map<String, String> params) {
//        System.out.println("tabid:" + tabid);
        String branchid = params.get("branchid");
        String tabid = params.get("tabid");
        String pageNum = params.get("pageNum");
        String pageSize = params.get("pageSize");
        return this.bOrderService.findByBranchOrder(branchid,tabid,pageNum,pageSize);
    }


}
