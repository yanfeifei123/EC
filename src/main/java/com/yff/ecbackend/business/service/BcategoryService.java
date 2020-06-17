package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
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
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private BproductService bproductsService;

    @Autowired
    private BphotoService bphotoService;

    @Autowired
    private BproductService bproductService;

    public List<Bcategory> findbusinessAll(Long branchid, String searchValue) {
//        System.out.println(branchid+"   "+searchValue);
        List<Bcategory> bcategoryList = new ArrayList<>();
        if (ToolUtil.isEmpty(searchValue)) {
            bcategoryList = this.bcategoryRepository.findbusinessAll(branchid);
            for (Bcategory bcategory : bcategoryList) {
                List<Bproduct> bproductsitems = bproductsService.findBproducts(bcategory.getId());
                bphotoService.setImagepath(bproductsitems);
                bcategory.setBproductsitems(bproductsitems);
            }
        } else {
            List<Bproduct> bproducts = bproductsService.findByBproductFuzzyquery(branchid, searchValue);

            this.filterChild(bproducts);

            Map<Long, List<Bproduct>> map = new HashMap<>();
            for (Bproduct bproduct : bproducts) {

                if (map.containsKey(bproduct.getCategoryid())) {
                    map.get(bproduct.getCategoryid()).add(bproduct);
                } else {
                    List<Bproduct> bproductsitems = new ArrayList<>();
                    bproductsitems.add(bproduct);
                    map.put(bproduct.getCategoryid(), bproductsitems);
                }
            }

            for (Long key : map.keySet()) {
                Bcategory bcategory = this.findOne(key);
                List<Bproduct> bproductsitems = map.get(key);
                bphotoService.setImagepath(bproductsitems);
                bcategory.setBproductsitems(bproductsitems);
                bcategoryList.add(bcategory);
            }
        }
//        System.out.println(JSON.toJSONString(bcategoryList));

        return bcategoryList;
    }


    private void filterChild(List<Bproduct> bproducts){

       HashSet<Long> idHashSet = new HashSet<>();
       Map<Long,Long> map = new HashMap<>();
       for(Bproduct bproduct : bproducts){
           if(ToolUtil.isNotEmpty(bproduct.getPid())){
               map.put(bproduct.getId(),bproduct.getId());
               idHashSet.add(bproduct.getPid());
           }
       }

       List<Bproduct> parent = new ArrayList<>();
       for(Long  pid :  idHashSet){
           Bproduct bproduct = this.bproductService.findOne(pid);
           parent.add(bproduct);
       }

        bproducts.removeIf(node  -> map.get(node.getId())!=null);

        bproducts.addAll(parent);

    }



    /*
      查询商品类型
     */
    public Object findByBcategory(String branchid,String searchValue) {
        List<Bcategory>  bcategories = new ArrayList<>();
        if(ToolUtil.isEmpty(searchValue)){
            bcategories=this.bcategoryRepository.findbusinessAll(Long.valueOf(branchid));
        }else{
           List<Bproduct> bproducts =  bproductService.findByBproductFuzzyquery(Long.valueOf(branchid),searchValue);
//           System.out.println("bproducts:"+bproducts.size());
           Map<Long,Long>  map =new HashMap<>();
           for(Bproduct bproduct : bproducts){
               if(!map.containsKey(bproduct.getCategoryid())){
                   map.put(bproduct.getCategoryid(),bproduct.getCategoryid());
                   Bcategory bcategory = this.findOne(bproduct.getCategoryid());
                   bcategories.add(bcategory);
               }
           }
//            System.out.println("map:"+map.size());
        }


        return bcategories;
    }

    public Object updatebcategory(Bcategory bcategorypojo) {
        bcategorypojo.setBuildtime(new Date());
        bcategorypojo = this.update(bcategorypojo);
        Map<String, Object> map = new HashMap<>();
        List<Bcategory> bcategories = (List<Bcategory>) this.findByBcategory(bcategorypojo.getBranchid().toString(),"");
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
