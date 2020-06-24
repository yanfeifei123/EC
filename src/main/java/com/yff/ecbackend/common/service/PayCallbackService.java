package com.yff.ecbackend.common.service;

import com.yff.core.util.SpringContextHolder;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.users.entity.Umemberrd;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.service.UmemberrdService;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.*;
/*
  支付回调服务
 */
@Service
public class PayCallbackService {



    @Autowired
    private UorderService uorderService;


    @Autowired
    private WeChatService weChatService;

    @Autowired
    private UmemberrdService umemberrdService;

    /**
     * 支付回调
     *
     * @param out_trade_no
     */
    @Async
    public void paymentCallbackMessage(String out_trade_no) {
        Messageinterface messageRepository = this.processingMessageRepository(out_trade_no);
        messageRepository.wxMessageProcessing(out_trade_no);
        messageRepository.doOrderPust(out_trade_no);
    }


    private Messageinterface processingMessageRepository(String out_trade_no){
        Uorder uorder = uorderService.findTradenoUorder(out_trade_no);
        Messageinterface messageRepository = null;
        if(ToolUtil.isNotEmpty(uorder)){
            return SpringContextHolder.getBean(MessageUorderImpl.class);
        }
        Umemberrd umemberrd = this.umemberrdService.findByUmemberrd(out_trade_no);
        if(ToolUtil.isNotEmpty(umemberrd)){
            return SpringContextHolder.getBean(MessagememberrdImpl.class);
        }
        return messageRepository;
    }

    /**
     * 退款回调
     *
     * @param req_info
     */
    @Async
    public void refundnotify(String req_info) {
        String result = weChatService.decryptReqinfo(req_info);
        try {
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            // 获取商户订单号
            String out_trade_no = map.get("out_trade_no");
            //允许退款
            this.uorderService.refundOpt(out_trade_no,true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
