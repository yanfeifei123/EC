package com.yff.ecbackend.common.service;


import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import com.yff.ecbackend.messagequeue.service.OrderMessageThreadingService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class MessagePushService {

    @Autowired
    private UorderService uorderService;

    @Autowired
    private UserService userService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private OrderMessageThreadingService orderMessageThreadingService;

    /**
     * 推送订单消息
     * @param request
     * @param branchid
     * @param page
     * @param orderid
     */
    public void msgSend(HttpServletRequest request , String branchid, String page, String orderid){
//        String templateId="X4ZGAyIVgnoOKUodwioOF1ocF4x1CYpU4KoI2H8VVgE";
//        Uorder uorder = this.uorderService.findOne(Long.valueOf(orderid));
//        uorder.setStatus(1);
//        this.uorderService.update(uorder);
//
//        Map<String, TemplateData> map = this.uorderService.findByOrderSendTempInfo(request, orderid);
//        User user = this.userService.findByBranchid(Long.valueOf(branchid));
//        String openid = "";
//        if (ToolUtil.isNotEmpty(user)) {
//            openid = user.getOpenid();
//        }
//        doOrderTask(branchid, openid, orderid);
//        weChatService.subscribeMessage(openid, templateId, page, map);
    }

//    private void doOrderTask(String branchid, String openid, String orderid) {
//        OrderMessageTemplate orderMessageTemplate = new OrderMessageTemplate();
//        orderMessageTemplate.setBranchid(Long.valueOf(branchid));
//        orderMessageTemplate.setOpenid(openid);
//        orderMessageTemplate.setOrderid(Long.valueOf(orderid));
//        orderMessageThreadingService.executeAysncOrderTask(orderMessageTemplate);
//    }

}
