package com.yff.ecbackend.users.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.common.service.Messageinterface;
import com.yff.ecbackend.users.entity.Umember;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
  订单会员支付
 */
@Service
public class UorderPayMemberService extends BaseService<Uorder, Long> {

    @Autowired
    private UmemberService umemberService;

    @Autowired
    private UserService userService;

    @Autowired
    private UorderService uorderService;


    @Autowired
    @Qualifier("messageUorderImpl")
    private Messageinterface messageUorderImpl;


    @Transactional(rollbackFor = Exception.class)
    public void payMember(String tradeno) {
        Uorder uorder = this.uorderService.findTradenoUorder(tradeno);
        float total_fee = uorder.getTotalfee();
        User user = this.userService.findByUserid(uorder.getOpenid());
        Long branchid = uorder.getBranchid();
        Umember umember = this.umemberService.findByUserUmember(user.getId(),branchid);
        float money = umember.getMoney() - total_fee;
        umember.setMoney(money);
        uorder.setStatus(1);
        uorder.setPaymode(1);  //会员支付

        this.umemberService.update(umember);
        this.uorderService.update(uorder);
        this.messageSend(tradeno);

    }
    @Async
    public void messageSend(String tradeno){
        //调用订单接口消息发送
        messageUorderImpl.wxMessageProcessing(tradeno);
        messageUorderImpl.doOrderPust(tradeno);
    }

}
