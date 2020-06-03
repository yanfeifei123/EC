package com.yff.ecbackend.messagequeue.service;


import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.messagequeue.config.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 消息堆栈服务
 */
@Service
@Slf4j
public class MessageStackService {

    private BlockingQueue<MessageTemplate> msgQueue = new LinkedBlockingQueue<>();  //消息队列
    /**
     * 消息队列入栈
     *
     * @param messageTemplate
     */
    public void executeAysncOrderTask(MessageTemplate messageTemplate) {
        try {
            msgQueue.put(messageTemplate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("创建新消息任务,订单ID： {} ,发送人：{} ,商家分店：{}", messageTemplate.getOrderid(), messageTemplate.getOpenid(), messageTemplate.getBranchid());
    }

    /**
     * 消息队列出栈
     * @return
     */
    public MessageTemplate take(Long  branchid) {
        try {
            MessageTemplate messageTemplate =null;
            Iterator<MessageTemplate> iterator = this.msgQueue.iterator();
            List<MessageTemplate> list = IteratorUtils.toList(iterator);
            for(MessageTemplate message:  list){
                if(branchid.equals(message.getBranchid())){
                    messageTemplate =  this.msgQueue.take();
                    break;
                }
            }
            if(ToolUtil.isNotEmpty(messageTemplate)){
                log.info("消息被取走,订单ID： {} ,发送人：{} ,商家分店：{},消息队列大小:{}", messageTemplate.getOrderid(), messageTemplate.getOpenid(), messageTemplate.getBranchid(),this.msgQueue.size());
                return messageTemplate;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
