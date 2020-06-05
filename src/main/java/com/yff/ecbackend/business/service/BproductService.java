package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yff.core.config.file.FileVerificationProperties;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.interfaces.PBEKey;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class BproductService extends BaseService<Bproduct, Long> {
    @Autowired
    private BproductsRepository bcategorysRepository;

    @Autowired
    private BphotoService bphotoService;


    @Autowired
    private BproductsRepository bproductsRepository;


    public Bproduct findOne(Long id) {
        Bproduct bproduct = this.bproductsRepository.findOne(id);
        if (ToolUtil.isNotEmpty(bproduct)) {
            this.bphotoService.setImagepath(bproduct);
        }
        return bproduct;
    }

    /**
     * 点餐用户端查询
     *
     * @param categoryid
     * @return
     */
    public List<Bproduct> findBproducts(Long categoryid) {
        List<Bproduct> bproducts = this.bcategorysRepository.findBproducts(categoryid);
        for (Bproduct bproduct : bproducts) {
            int msales = this.bcategorysRepository.countMsales(bproduct.getId());
            bproduct.setMsales(msales);
        }
        return bproducts;
    }

    /**
     * 商家用户端查询
     *
     * @param categoryid
     * @return
     */
    public List<Bproduct> findinSetmealBproducts(Long categoryid, String searchValue) {
        List<Bproduct> bproducts = new ArrayList<>();
        if (ToolUtil.isEmpty(searchValue))
            bproducts = this.bcategorysRepository.findinSetmealBproducts(categoryid);
        else
            bproducts = this.bproductsRepository.findByBproductCategoryidFuzzyquery(categoryid, searchValue);

        for (Bproduct bproduct : bproducts) {
            int msales = this.bcategorysRepository.countMsales(bproduct.getId());
            bproduct.setMsales(msales);
        }
        return bproducts;
    }


    /**
     * 查询组合套餐
     *
     * @param pid
     * @return
     */
    public Bproduct findByproductPackage(Long pid) {
        List<Bproduct> child = this.bcategorysRepository.findByproductPackage(pid);
        Bproduct bproduct = this.findOne(pid);
        if (ToolUtil.isNotEmpty(bproduct)) {
            this.bphotoService.setImagepath(bproduct);
            this.bphotoService.setImagepath(child);
            bproduct.setBproductsitems(child);
        }
//        String s = JSON.toJSONString(bproduct);
//        System.out.println(s);
        return bproduct;
    }

    /**
     * 通过分店id查询分店商品
     *
     * @param branchid
     * @return
     */
    public List<Bproduct> findByBproductToBbranch(Long branchid) {
        return this.bcategorysRepository.findByBproductToBbranch(branchid);
    }

    public Bproduct updatebproduct(String bproduct) {
        Bproduct obj = JSON.parseObject(bproduct, Bproduct.class);
        obj.setBuildtime(new Date());
        return this.update(obj);
    }

    @Transactional(rollbackFor = Exception.class)
    public Bproduct updatebproductUpLoadFile(String openid, String bproduct, MultipartFile multipartFile) {
//        System.out.println(bproduct);
        JSONObject jsonObject = JSON.parseObject(bproduct);
        String imagepath = jsonObject.getString("imagepath");
        Bproduct bp = JSON.parseObject(bproduct, Bproduct.class);
        bp.setBuildtime(new Date());
        bp = this.update(bp);
        this.bphotoService.photoUpload(multipartFile, openid, bp.getId(), imagepath);
        return bp;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object deleteBproduct(String bproductid) {
        List<Bproduct> child = this.bcategorysRepository.findByproductPackage(Long.valueOf(bproductid));
        if (ToolUtil.isNotEmpty(child)) {
            return -1;
        }
        Bproduct bp = this.findOne(Long.valueOf(bproductid));
        if (ToolUtil.isNotEmpty(bp)) {
            this.bphotoService.deleteBphoto(bp.getId());
            this.delete(bp);
        }
        return 1;
    }


    public Object choosePackage(String bproductid, String categoryid) {
        List<Bproduct> list = new ArrayList<>();
        List<Bproduct> child = new ArrayList<>();
        if (ToolUtil.isEmpty(bproductid)) {
            list = this.bproductsRepository.findinSetmealBproducts(Long.valueOf(categoryid));
        } else {
            list = this.bproductsRepository.findByBproductToPackage(Long.valueOf(categoryid), Long.valueOf(bproductid));
            child = this.bcategorysRepository.findByproductPackage(Long.valueOf(bproductid));
        }

        System.out.println(list.size() + "   " + child.size());
        Map<Long, JSONObject> map = new HashMap<>();
        JSONArray jsonArray = new JSONArray();

        for (Bproduct bproduct : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", bproduct.getId());
            jsonObject.put("name", bproduct.getName());
            jsonObject.put("checked", false);
            jsonArray.add(jsonObject);
        }

        for (Bproduct bproduct : child) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", bproduct.getId());
            jsonObject.put("name", bproduct.getName());
            jsonObject.put("checked", true);
            if (!map.containsKey(bproduct.getId())) {
                map.put(bproduct.getId(), jsonObject);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;


    }

    /*
     绑定套餐关联
     */

    public Object updateBindingSubbproduct(String bproductid, String jsonlist) {
        List<Bproduct> bproducts = this.bcategorysRepository.findByproductPackage(Long.valueOf(bproductid));
        for (Bproduct bproduct : bproducts) {
            bproduct.setPid(null);
        }
        this.update(bproducts);


        JSONArray jsonArray = JSON.parseArray(jsonlist);
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String id = jsonArray.getJSONObject(i).getString("id");
            String checked = jsonArray.getJSONObject(i).getString("checked");
//            System.out.println("id:" +  id+"   checked:"+checked);
            if (Boolean.valueOf(checked))
                ids.add(Long.valueOf(id));
        }
        List<Bproduct> childs = this.bproductsRepository.findByBproductIdin(ids);
        for (Bproduct bproduct : childs) {
            bproduct.setPid(Long.valueOf(bproductid));
        }
        this.update(childs);
        Bproduct parentbproduct = this.bcategorysRepository.findOne(Long.valueOf(bproductid));
        if (ids.size() != 0) {
            parentbproduct.setPackages("1");
        } else {
            parentbproduct.setPackages("0");
        }
        this.update(parentbproduct);

        return 1;

    }

    public List<Bproduct> findByBproductFuzzyquery(Long branchid, String searchValue) {
        return this.bproductsRepository.findByBproductFuzzyquery(branchid, searchValue);
    }


}
