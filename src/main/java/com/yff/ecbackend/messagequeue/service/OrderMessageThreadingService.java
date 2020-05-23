package com.yff.ecbackend.messagequeue.service;


import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class OrderMessageThreadingService {

    private BlockingQueue<OrderMessageTemplate> msgQueue = new LinkedBlockingQueue<>();  //消息队列

    @Autowired
    private UorderService uorderService;

    @Autowired
    private UserService userService;

    @Autowired
    private WeChatService weChatService;

    /**
     * 异步更改订单状态并且推送消息
     * @param request
     * @param out_trade_no
     */
    @Async
    public void MsgSend(HttpServletRequest request , String out_trade_no){
        String templateId="X4ZGAyIVgnoOKUodwioOF1ocF4x1CYpU4KoI2H8VVgE";

        Uorder uorder = this.uorderService.updateUorder(out_trade_no);

        String page="/pages/business/tabBar/tabBar?orderid="+uorder.getId();

        Map<String, TemplateData> map = this.uorderService.findByOrderSendTempInfo(request, uorder);
        User user = this.userService.findByBranchid(uorder.getBranchid());
        String openid = "";
        if (ToolUtil.isNotEmpty(user)) {
            openid = user.getOpenid();
        }
        this.doOrderTask(uorder.getBranchid(), openid, uorder.getId());
        this.weChatService.subscribeMessage(openid, templateId, page, map);
    }

    /**
     * 消息推送给本系统
     * @param branchid
     * @param openid
     * @param orderid
     */
    private void doOrderTask(Long branchid, String openid, Long orderid) {
        OrderMessageTemplate orderMessageTemplate = new OrderMessageTemplate();
        orderMessageTemplate.setBranchid(branchid);
        orderMessageTemplate.setOpenid(openid);
        orderMessageTemplate.setOrderid(orderid);
        this.executeAysncOrderTask(orderMessageTemplate);
    }


    /**
     * 消息队列入栈
     *
     * @param orderMessageTemplate
     */
    public void executeAysncOrderTask(OrderMessageTemplate orderMessageTemplate) {
        try {
            msgQueue.put(orderMessageTemplate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("创建新订单任务,订单ID： {} ,发送人：{} ,商家分店：{}", orderMessageTemplate.getOrderid(), orderMessageTemplate.getOpenid(), orderMessageTemplate.getBranchid());
    }

    /**
     * 消息队列出栈
     * @return
     */
//    @Async
    public OrderMessageTemplate take(Long  branchid) {
        try {
            OrderMessageTemplate orderMessage =null;
            Iterator<OrderMessageTemplate> iterator = this.msgQueue.iterator();
            List<OrderMessageTemplate> list = IteratorUtils.toList(iterator);
            for(OrderMessageTemplate orderMessageTemplate:  list){
                if(branchid.equals(orderMessageTemplate.getBranchid())){
                    orderMessage =  this.msgQueue.take();
                    break;
                }
            }
            if(ToolUtil.isNotEmpty(orderMessage)){
                log.info("订单被取走,订单ID： {} ,发送人：{} ,商家分店：{},消息队列大小:{}", orderMessage.getOrderid(), orderMessage.getOpenid(), orderMessage.getBranchid(),this.msgQueue.size());
                return orderMessage;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
