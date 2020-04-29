package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bphoto;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.repository.BproductsRepository;
import com.yff.ecbackend.common.service.WeChatService;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class BproductService extends BaseService<Bproduct, Long> {
    @Autowired
    private BproductsRepository bcategorysRepository;

    @Autowired
    private BphotoService bphotoService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private Parameterconf parameterconf;


    public List<Bproduct> findBproducts(Long categoryid) {
        return this.bcategorysRepository.findBproducts(categoryid);
    }

    /**
     * 查询组合套餐
     *
     * @param pid
     * @return
     */
    public Bproduct findByproductPackage(HttpServletRequest request, Long pid) {
        List<Bproduct> child = this.bcategorysRepository.findByproductPackage(pid);
        Bproduct bproduct = this.findOne(pid);
        if (ToolUtil.isNotEmpty(bproduct)) {
            this.bphotoService.setImagepath(request, bproduct);
            this.bphotoService.setImagepath(request, child);
            bproduct.setBproductsitems(child);
        }
//        String s = JSON.toJSONString(bproduct);
//        System.out.println(s);
        return bproduct;
    }

    /**
     * 通过分店id查询分店商品
     * @param branchid
     * @return
     */
    public List<Bproduct> findByBproductToBbranch(Long branchid){
        return this.bcategorysRepository.findByBproductToBbranch(branchid);
    }


}