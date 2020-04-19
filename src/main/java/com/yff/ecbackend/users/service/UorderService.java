package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.jsf.FacesContextUtils;

import java.util.*;

@Service
public class UorderService extends BaseService<Uorder, Long> {

    @Autowired
    private UorderRepository uorderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UordertailService uordertailService;

    public List<Uorder> findUserOrder(String openid) {
        return this.uorderRepository.findUserOrder(openid);
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateUserOrder(String openid, String shoppingcart, String bid, String totalfee, String branchid, String isself, String discount) {
//        System.out.println(shoppingcart);
        User user = this.userService.getUser(openid);
        Uorder uorder = this.updateUorder(user, bid, branchid, Integer.parseInt(isself), Float.valueOf(totalfee), Float.valueOf(discount),shoppingcart);
        this.uordertailService.updateUordertail(uorder.getId(), shoppingcart);
        return 1;
    }


    /**
     * 创建用户订单
     *
     * @param user
     * @param bid
     * @param branchid
     * @param isself
     * @param totalfee
     * @return
     */
    private Uorder updateUorder(User user, String bid, String branchid, int isself, float totalfee, float discount,String josn) {
        Uorder uorder = new Uorder();
        uorder.setBid(Long.valueOf(bid));
        uorder.setBranchid(Long.valueOf(branchid));
        uorder.setIscomplete(0);
        uorder.setOpenid(user.getOpenid());
        uorder.setStatus(1);
        uorder.setTotalfee(totalfee);
        uorder.setUserid(user.getId());
        uorder.setOdr(1);
        uorder.setBuildtime(new Date());
        uorder.setSelf(isself);
        uorder.setDiscount(discount);
        uorder.setJson(josn);
        return this.uorderRepository.save(uorder);
    }


}
