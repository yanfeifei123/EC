package com.yff.ecbackend.common.service;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Umember;
import com.yff.ecbackend.users.entity.Umemberrd;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UaddressService;
import com.yff.ecbackend.users.service.UmemberService;
import com.yff.ecbackend.users.service.UmemberrdService;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 处理充值
 */
@Service
public class MessagememberrdImpl implements Messageinterface {

    private DecimalFormat df = new DecimalFormat("#.00");

    @Autowired
    private Parameterconf parameterconf;
    @Autowired
    private UmemberrdService umemberrdService;
    @Autowired
    private UmemberService umemberService;
    @Autowired
    private UserService userService;
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UaddressService uaddressService;
    @Autowired
    private MessageStackService messageStackService;

    @Override
    public void wxMessageProcessing(String out_trade_no) {
        String templateId = this.parameterconf.getNewmsgtemplateId();
        Umemberrd umemberrd = this.umemberrdService.findByUmemberrd(out_trade_no);
        String page = "/pages/meal/umemberrd/umemberrd?umemberrdid" + umemberrd.getId();
        String openid="oM_GB4hz64RW-nZg5hJLaSUc7Vmc";
//        List<User> userOperators = this.userService.findByBranchid(umemberrd.getBranchid());
//        String openid = "";
//        if (ToolUtil.isNotEmpty(userOperators)) {
//            openid = userOperators.get(0).getOpenid();   //消息推送给谁
//            System.out.println("微信接收消息openid：" + openid);
//        }
        Map<String, TemplateData> map = this.wxbuilderMessageTemplate(out_trade_no);
        this.weChatService.subscribeMessage(openid, templateId, page, map);
    }

    @Override
    public Map<String, TemplateData> wxbuilderMessageTemplate(String out_trade_no) {
        Umemberrd umemberrd = this.umemberrdService.findByUmemberrd(out_trade_no);
        Map<String, TemplateData> map = new HashMap<>();
        map.put("character_string1", new TemplateData(out_trade_no));
        map.put("phrase2", new TemplateData("会员开通"));
        map.put("amount4", new TemplateData(df.format(umemberrd.getMoney())));
        map.put("thing6", new TemplateData("充值"));
        User user = this.userService.findOne(umemberrd.getUserid());
        Uaddress uaddress = this.uaddressService.findByUaddress(user.getId()).get(0);
        map.put("name7", new TemplateData(uaddress.getName() + "(" + uaddress.getGender() + ")"));
        return map;
    }

    @Override
    public void doOrderPust(String out_trade_no) {
        String type = "recharge";
        Umemberrd umemberrd = this.umemberrdService.findByUmemberrd(out_trade_no);
        User user = this.userService.findOne(umemberrd.getUserid());
        if(user.getMember()==0){
            user.setMember(1);  //更新用户为会员
            this.userService.update(user);
        }

        umemberrd.setStatus(1); //更新已支付
        this.umemberrdService.update(umemberrd);

        Umember umember = this.umemberService.findByUserUmember(user.getId(), umemberrd.getBranchid());

        if (ToolUtil.isEmpty(umember)) {
            umember = new Umember();
            umember.setOdr(0);
            umember.setBranchid(umemberrd.getBranchid());
            umember.setMoney(umemberrd.getMoney());
            umember.setUserid(user.getId());
        } else {
            float money = umember.getMoney() + umemberrd.getMoney();
            umember.setMoney(money);
        }
        umember.setBuildtime(new Date());
        this.umemberService.update(umember); //更新会员金额

        this.messageStackService.doOrderTask(umemberrd.getBranchid(), user.getOpenid(), umemberrd.getId(), type);
    }
}
