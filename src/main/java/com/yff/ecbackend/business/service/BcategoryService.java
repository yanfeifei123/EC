package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bcategory;
import com.yff.ecbackend.business.entity.Bphoto;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.repository.BcategoryRepository;
import com.yff.ecbackend.common.service.WeChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class BcategoryService extends BaseService<Bcategory, Long> {

    @Autowired
    private Parameterconf parameterconf;

    @Autowired
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private BproductService bproductsService;

    @Autowired
    private BphotoService bphotoService;

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private BproductService bproductService;

    public List<Bcategory> findbusinessAll(HttpServletRequest request, Long branchid) {

        List<Bcategory> bcategoryList = this.bcategoryRepository.findbusinessAll(branchid);
        for (Bcategory bcategory : bcategoryList) {
            List<Bproduct> bproductsitems = bproductsService.findBproducts(bcategory.getId());
            bphotoService.setImagepath(request, bproductsitems);
            bcategory.setBproductsitems(bproductsitems);
        }

//        System.out.println(JSON.toJSONString(bcategoryList));

        return bcategoryList;
    }


    /**
     * 通过内存查询提高效率
     *
     * @param allBproducts
     * @param categoryid
     * @return
     */
    private List<Bproduct> findByBproducts(List<Bproduct> allBproducts, Long categoryid) {
        List<Bproduct> bproductsitems = new ArrayList<>();
        for (Bproduct bproducts : allBproducts) {
            if (bproducts.getCategoryid().equals(categoryid)) {
                bproductsitems.add(bproducts);
            }
        }
        return bproductsitems;
    }

    /*
      查询商品类型
     */
    public Object findByBcategory(String branchid) {
        return this.bcategoryRepository.findbusinessAll(Long.valueOf(branchid));
    }

    public Object updatebcategory(Bcategory bcategorypojo) {
        bcategorypojo.setBuildtime(new Date());
        bcategorypojo = this.update(bcategorypojo);
        Map<String, Object> map = new HashMap<>();
        List<Bcategory> bcategories = (List<Bcategory>) this.findByBcategory(bcategorypojo.getBranchid().toString());
        map.put("bcategories", bcategories);
        map.put("id", bcategorypojo.getId());
        return map;
    }

    public int deletebcategory(String bcategoryid) {
        Bcategory bcategory = this.findOne(Long.valueOf(bcategoryid));
        if (ToolUtil.isNotEmpty(bcategory)) {
            List<Bproduct> bproducts = this.bproductService.findBproducts(bcategory.getId());
            if (bproducts.size() == 0) {
                return this.delete(bcategory) == true ? 1 : 0;
            } else {
                return -1;
            }
        }
        return 1;
    }


}
