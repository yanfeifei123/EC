package com.yff.ecbackend.messagequeue.service;


import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class OrderMessageThreadingService {

    private BlockingQueue<OrderMessageTemplate> msgQueue = new LinkedBlockingQueue<>();  //消息队列



    private Logger logger = LoggerFactory.getLogger(OrderMessageThreadingService.class);



    /**
     * 消息队列入栈
     *
     * @param orderMessageTemplate
     */
    @Async
    public void executeAysncOrderTask(OrderMessageTemplate orderMessageTemplate) {
        try {
            msgQueue.put(orderMessageTemplate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("创建新订单任务,订单ID： {} ,发送人：{} ,商家分店：{}", orderMessageTemplate.getOrderid(), orderMessageTemplate.getOpenid(), orderMessageTemplate.getBranchid());
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
                System.out.println("消息队列大小："+this.msgQueue.size());
                logger.info("订单被取走,订单ID： {} ,发送人：{} ,商家分店：{}", orderMessage.getOrderid(), orderMessage.getOpenid(), orderMessage.getBranchid());
                return orderMessage;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
