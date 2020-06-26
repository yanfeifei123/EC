package com.yff.ecbackend.users.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.Umemberrd;
import com.yff.ecbackend.users.repository.UmemberrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UmemberrdService extends BaseService<Umemberrd,Long> {

    @Autowired
    private UmemberrdRepository umemberrdRepository;

    @Autowired
    private WeChatService weChatService;


    /**
     * 通过商户id号查询充值
     * @param tradeno
     * @return
     */
    public Umemberrd findByUmemberrd(String tradeno){
        return this.umemberrdRepository.findByUmemberrd(tradeno);
    }


    public List<Umemberrd> findByUserUmemberrd(Long userid){
        return this.umemberrdRepository.findByUserUmemberrd(userid);
    }

    /**
     * 添加充值
     * @param umemberrd
     * @return
     */
    public Umemberrd builderUmemberrd(Umemberrd umemberrd){
         if(umemberrd.isNew()){
             umemberrd.setBuildtime(new Date());
             String tradeno = this.weChatService.createOutTradeno();
             umemberrd.setTradeno(tradeno);
         }
         return this.update(umemberrd);
    }


    public int findBycheckupOrderMember(Long branchid){
        return this.umemberrdRepository.findBycheckupOrderMember(branchid);
    }


    public void updateCheckup(Long branchid){
         this.umemberrdRepository.updateCheckup(branchid);
    }
}
