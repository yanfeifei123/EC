package com.yff.ecbackend.common.service;

import com.yff.ecbackend.common.pojo.TemplateData;
import java.util.Map;

public interface Messageinterface {

    /**
     * 微信消息处理
     * @param out_trade_no
     */
    public abstract void wxMessageProcessing(String out_trade_no);

    /**
     * 构建微信消息模板
     * @param out_trade_no
     * @return
     */
    public abstract Map<String, TemplateData> wxbuilderMessageTemplate(String out_trade_no);


    /**
     * 订单推送
     * @param out_trade_no
     */
    public abstract void doOrderPust(String out_trade_no);

}
