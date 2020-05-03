package com.yff.ecbackend.messagequeue.service;


import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class OrderMessageThreadingService {

    public static final int QUEUE_MAX_SIZE = 100;
    private BlockingQueue<OrderMessageTemplate> msgQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);  //消息队列

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
        logger.info("新订单任务,订单ID： {} ,发送人：{} ,商家分店：{}", orderMessageTemplate.getOrderid(), orderMessageTemplate.getOpenid(), orderMessageTemplate.getBranchid());
    }

    /**
     * 消息队列出栈
     *
     * @return
     */
    public OrderMessageTemplate take() {
        try {
            if(this.msgQueue.size()>0){
                OrderMessageTemplate orderMessageTemplate = this.msgQueue.take();
                return orderMessageTemplate;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
