package com.yff.ecbackend.users.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.service.BproductService;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.repository.UordertailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UordertailService extends BaseService<Uordertail, Long> {

    @Autowired
    private UordertailRepository uordertailRepository;

    @Autowired
    private BproductService bproductService;

    public List<Uordertail> updateUordertail(Long orderid, String shoppingcart) {
        JSONArray jsonArray = JSON.parseArray(shoppingcart);
        List<Uordertail> uordertails = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Long productid = Long.valueOf(jsonObject.getString("id"));
            Integer num = Integer.parseInt(jsonObject.getString("num"));
            JSONArray items = JSON.parseArray(jsonObject.getString("items"));

            if (num != 1) {  // 多个商品同一类型
                for (int j = 1; j <= num; j++) {

                    Bproduct bproduct = bproductService.findOne(productid);
                    int ismeal = ToolUtil.isNotEmpty(items) ? 1 : 0;

                    Uordertail uordertail = this.assembleUordertail(orderid, bproduct.getMemberprice(), bproduct.getPrice(), bproduct.getId(), j, null, ismeal);
                    uordertail = this.uordertailRepository.save(uordertail);

                    if (ismeal == 1) {  //如果包含套餐
                        JSONArray childshoppingcarts = JSON.parseArray(items.getJSONObject(j - 1).getString("shoppingcart"));
                        for (int k = 0; k < childshoppingcarts.size(); k++) {
                            JSONObject childshoppingcart = childshoppingcarts.getJSONObject(k);
                            Integer number = Integer.parseInt(childshoppingcart.getString("num")); //数量
                            Long productid1 = Long.valueOf(childshoppingcart.getString("id"));  //商品id
                            for (int l = 1; l <= number; l++) {
                                Bproduct bproductc = bproductService.findOne(productid1);
                                Uordertail uordertailc = this.assembleUordertail(orderid, bproductc.getMemberprice(), bproductc.getPrice(), bproductc.getId(), l, uordertail.getId(), 0);
                                this.uordertailRepository.save(uordertailc);
                            }
                        }
                    }
                }
            } else {   //计算单个不是套餐的
                float tprice = Float.valueOf(jsonObject.getString("tprice"));
                float tmemberprice = Float.valueOf(jsonObject.getString("tmemberprice"));
                Uordertail uordertail = this.assembleUordertail(orderid, tmemberprice, tprice, productid, i + 1, null, 0);
                uordertails.add(uordertail);
            }
        }
        return this.update(uordertails);
    }


    public Uordertail assembleUordertail(Long orderid, float memberprice, float price, Long productid, int odr, Long pid, int ismeal) {
        Uordertail uordertail = new Uordertail();
        uordertail.setBuildtime(new Date());
        uordertail.setOrderid(orderid);
        uordertail.setMemberprice(memberprice);
        uordertail.setPrice(price);
        uordertail.setProductid(productid);
        uordertail.setOdr(odr);
        uordertail.setIsmeal(ismeal);
        uordertail.setPid(pid);
        return uordertail;
    }

}
