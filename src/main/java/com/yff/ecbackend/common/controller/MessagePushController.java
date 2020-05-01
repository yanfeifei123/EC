package com.yff.ecbackend.common.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UorderService;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 小程序消息推送管理
 */
@Controller
@RequestMapping("/message")
public class MessagePushController {

    @Autowired
    private WeChatService weChatService;


    @Autowired
    private UorderService uorderService;


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sendOrderPayInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object send(HttpServletRequest request, String branchid, String templateId, String page, String orderid) {
        Map<String, TemplateData> map = this.uorderService.findByOrderSendTempInfo(request, orderid);
        User user = this.userService.findByBranchid(Long.valueOf(branchid));
        String openid="";
        if(ToolUtil.isNotEmpty(user)){
            openid =  user.getOpenid();
        }
        return weChatService.subscribeMessage(openid, templateId, page, map);
    }

}
