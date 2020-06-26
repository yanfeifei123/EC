package com.yff.ecbackend.users.controller;


import com.alibaba.fastjson.JSON;
import com.yff.ecbackend.users.entity.Umemberrd;
import com.yff.ecbackend.users.service.UmemberService;
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


    @Autowired
    private UmemberService umemberService;


    /**
     * 创建充值订单
     *
     * @return
     */
    @RequestMapping(value = "/builderUmemberrd", method = RequestMethod.POST)
    @ResponseBody
    public Object builderUmemberrd(String memberrd) {
        Umemberrd umemberrd = JSON.parseObject(memberrd, Umemberrd.class);
        return this.umemberrdService.builderUmemberrd(umemberrd);
    }

    /**
     * 查询商家会员
     * @param branchid
     * @return
     */
    @RequestMapping(value = "/findByUserbranchidUmember", method = RequestMethod.POST)
    @ResponseBody
    public Object findByUserbranchidUmember(String branchid) {
        return umemberService.findByUserbranchidUmember(Long.valueOf(branchid));
    }



    @RequestMapping(value = "/findByUserUmember", method = RequestMethod.POST)
    @ResponseBody
    public Object findByUserUmember(String  userid, String branchid){
         return umemberService.findByUserUmember(Long.valueOf(userid) ,Long.valueOf(branchid));
    }

    /**
     * 查询未查看的充值记录
     * @param branchid
     * @return
     */
    @RequestMapping(value = "/findBycheckupOrderMember", method = RequestMethod.POST)
    @ResponseBody
    public Object findBycheckupOrderMember(String branchid){
        return this.umemberrdService.findBycheckupOrderMember(Long.valueOf(branchid));
    }

    @RequestMapping(value = "/updateCheckup", method = RequestMethod.POST)
    @ResponseBody
    public int updateCheckup(String branchid){
        this.umemberrdService.updateCheckup(Long.valueOf(branchid));
        return 1;
    }










}
