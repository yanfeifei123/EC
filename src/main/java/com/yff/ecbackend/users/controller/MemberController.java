package com.yff.ecbackend.users.controller;


import com.alibaba.fastjson.JSON;
import com.yff.ecbackend.users.entity.Umemberrd;
import com.yff.ecbackend.users.service.UmemberrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private UmemberrdService umemberrdService;


    /**
     * 创建充值订单
     * @return
     */
    @RequestMapping(value = "/builderUmemberrd", method = RequestMethod.POST)
    @ResponseBody
    public Object builderUmemberrd(String memberrd) {
        Umemberrd umemberrd = JSON.parseObject(memberrd, Umemberrd.class);
        return this.umemberrdService.builderUmemberrd(umemberrd);
    }


}
