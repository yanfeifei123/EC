package com.yff.ecbackend.common.service;



import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.messagequeue.config.MessageTemplate;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uorderr;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UorderrService;
import com.yff.ecbackend.users.service.UserService;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单支付消息回调服务
 */
@Service
public class MessagePushService {

    @Autowired
    private UorderService uorderService;

    @Autowired
    private UserService userService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private MessageStackService messageStackService;

    @Autowired
    private Parameterconf parameterconf;

    /**
     * 异步调用
     *
     */
    @Async
    public void paymentCallbackMessage(String out_trade_no) {
        this.doOrderPust(out_trade_no);
        this.wxMsghandle(out_trade_no);
    }

    @Async
    public void doOrderPust(String out_trade_no) {
        Uorder uorder = this.uorderService.updateUorder(out_trade_no);
        String type = "order";
        this.doOrderTask(uorder.getBranchid(), uorder.getOpenid(), uorder.getId(), type);
    }

    /**
     * 本系统订单消息推送
     *
     * @param branchid
     * @param openid   下单用户
     * @param orderid
     */
    public void doOrderTask(Long branchid, String openid, Long orderid, String type) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setBranchid(branchid);
        messageTemplate.setOpenid(openid);
        messageTemplate.setOrderid(orderid);
        messageTemplate.setType(type);
        this.messageStackService.executeAysncOrderTask(messageTemplate);
    }

    /**
     * 微信模板消息处理
     *
     * @param out_trade_no
     */
    @Async
    public void wxMsghandle(String out_trade_no) {
        String templateId = this.parameterconf.getNewmsgtemplateId();
        Uorder uorder = this.uorderService.updateUorder(out_trade_no);
        String page = "/pages/business/tabBar/tabBar?orderid=" + uorder.getId();
        List<User> userOperators = this.userService.findByBranchid(uorder.getBranchid());

        String openid = "";
        if(ToolUtil.isNotEmpty(userOperators)){
            openid = userOperators.get(0).getOpenid();   //消息推送给谁
        }
        Map<String, TemplateData> map = this.uorderService.findByOrderSendTempInfo(uorder);
        this.weChatService.subscribeMessage(openid, templateId, page, map);

    }


    /**
     * 退款业务处理
     * @param req_info
     */
    @Async
    public void refundnotify(String req_info){
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
