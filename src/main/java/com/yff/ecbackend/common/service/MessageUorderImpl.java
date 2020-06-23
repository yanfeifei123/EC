package com.yff.ecbackend.common.service;

import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UaddressService;
import com.yff.ecbackend.users.service.UorderOdrService;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UserService;
import com.yff.ecbackend.users.view.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 专门处理点餐订单
 */
@Service
public class MessageUorderImpl implements Messageinterface {

    private DecimalFormat df = new DecimalFormat("#.00");


    @Autowired
    private MessageStackService messageStackService;

    @Autowired
    private UorderService uorderService;


    @Autowired
    private UaddressService uaddressService;


    @Autowired
    private WeChatService weChatService;

    @Autowired
    private UserService userService;

    @Autowired
    private Parameterconf parameterconf;

    @Override
    public void wxMessageProcessing(String out_trade_no) {
        String templateId = this.parameterconf.getNewmsgtemplateId();
        Uorder uorder = this.uorderService.updateUorder(out_trade_no);
        String page = "/pages/business/ordermd/ordermd?orderid=" + uorder.getId();
        List<User> userOperators = this.userService.findByBranchid(uorder.getBranchid());

        String openid = "";
        if (ToolUtil.isNotEmpty(userOperators)) {
            openid = userOperators.get(0).getOpenid();   //消息推送给谁
            System.out.println("微信接收消息openid："+openid);
        }
        Map<String, TemplateData> map = this.wxbuilderMessageTemplate(out_trade_no);
        this.weChatService.subscribeMessage(openid, templateId, page, map);
    }

    @Override
    public Map<String, TemplateData> wxbuilderMessageTemplate(String out_trade_no) {

        Uorder uorder = uorderService.findTradenoUorder(out_trade_no);
        Map<String, TemplateData> map = new HashMap<>();
        map.put("character_string1", new TemplateData(uorder.getTradeno()));
        String orderType = uorder.getSelf() == 1 ? "到店自取" : "外卖配送";
        map.put("phrase2", new TemplateData(orderType));
        map.put("amount4", new TemplateData(df.format(uorder.getTotalfee())));
        List<OrderItem> orderItems = this.uorderService.orderByOrderDetailed(uorder.getId().toString());
        String names = "";
        String istc = "";
        for (OrderItem orderItem : orderItems) {
            istc = orderItem.getIsmeal() == 1 ? "（套餐）" : "";
            if (names.equals("")) {
                names = orderItem.getName() + istc;
            } else {
                names += "," + orderItem.getName() + istc;
            }
        }
        map.put("thing6", new TemplateData(names));
        Uaddress uaddress = this.uaddressService.findOne(uorder.getUaddressid());
        map.put("name7", new TemplateData(uaddress.getName() + "(" + uaddress.getGender() + ")"));
        return map;
    }

    @Override
    public void doOrderPust(String out_trade_no) {
        Uorder uorder = this.uorderService.updateUorder(out_trade_no);
        String type = "order";
        this.messageStackService.doOrderTask(uorder.getBranchid(), uorder.getOpenid(), uorder.getId(), type);
    }
}
