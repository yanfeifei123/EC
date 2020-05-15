package com.yff.ecbackend.users.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.service.BphotoService;
import com.yff.ecbackend.business.service.BproductService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.repository.UordertailRepository;
import com.yff.ecbackend.users.view.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;

@Service
public class UordertailService extends BaseService<Uordertail, Long> {

    @Autowired
    private UordertailRepository uordertailRepository;

    @Autowired
    private BproductService bproductService;

    @Autowired
    private UorderService uorderService;

    @Autowired
    private BphotoService bphotoService;



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
                Bproduct bproduct = bproductService.findOne(productid);
                float tprice = bproduct.getPrice();
                float tmemberprice = bproduct.getMemberprice();

                //计算单个套餐
                int ismeal = ToolUtil.isNotEmpty(items) ? 1 : 0;
                if (ismeal == 1) {
                    Uordertail uordertail = this.assembleUordertail(orderid, tmemberprice, tprice, productid, i + 1, null, ismeal);
                    uordertail = this.uordertailRepository.save(uordertail);
                    JSONArray childshoppingcarts = JSON.parseArray(items.getJSONObject(0).getString("shoppingcart"));
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
                } else {
                    Uordertail uordertail = this.assembleUordertail(orderid, tmemberprice, tprice, productid, i + 1, null, 0);
                    uordertails.add(uordertail);
                }
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

    /**
     * 查询订单明细
     *
     * @param request
     * @param orderid
     * @return
     */
    public List<Uordertail> findByUordertail(HttpServletRequest request, Long orderid) {
        List<Uordertail> uordertails = this.uordertailRepository.findByUordertail(orderid);
        Uorder uorder = this.uorderService.findOne(orderid);
        List<Bproduct> bproducts = this.bproductService.findByBproductToBbranch(uorder.getBranchid());
        for (Uordertail uordertail : uordertails) {
            for (Bproduct bproduct : bproducts) {
                if (uordertail.getProductid().equals(bproduct.getId())) {
                    uordertail.setName(bproduct.getName());
                }
            }
        }
        this.bphotoService.setUordertailImagePath(request, uordertails);
        return uordertails;
    }

    /**
     * 已经查询好的List<Uordertail>
     *
     * @param uordertails
     * @return
     */
    public List<OrderItem> detailedStatisticsToOrderItem(List<Uordertail> uordertails) {
        List<OrderItem> orderItems = new ArrayList<>();
        boolean f = false;
        for (Uordertail uordertail : uordertails) {
            OrderItem orderItem = this.initOrderItemToUordertail(uordertail);
            if (uordertail.getIsmeal() == 1) {  //套餐
                List<Uordertail> childUordertails = findByChildUordertail(uordertail, uordertails);
                List<OrderItem> childOrderItem = this.statisticsToOrderItem(childUordertails);
                orderItem.setOrderItems(childOrderItem);
                orderItems.add(orderItem);
                f=true;
            }
        }
        if(f){
            for (OrderItem orderItem : orderItems) {
                uordertails.removeIf(node -> node.getProductid().equals(orderItem.getProductid()));
                for (OrderItem child : orderItem.getOrderItems()) {
                    uordertails.removeIf(node -> node.getProductid().equals(child.getProductid()));
                }
            }
            List<OrderItem> orderItems1 = this.statisticsToOrderItem(uordertails);
            orderItems.addAll(orderItems1);
        }else{
            List<OrderItem>   orderItemList = this.statisticsToOrderItem(uordertails);
            orderItems.addAll(orderItemList);
        }

        return orderItems;
    }

    /**
     * 查找套餐
     *
     * @param puordertail
     * @param uordertails
     * @return
     */
    private List<Uordertail> findByChildUordertail(Uordertail puordertail, List<Uordertail> uordertails) {
        List<Uordertail> childUordertails = new ArrayList<>();
        for (Uordertail uordertail : uordertails) {
            if (ToolUtil.isNotEmpty(uordertail.getPid())) {
                if (puordertail.getId().equals(uordertail.getPid())) {
                    childUordertails.add(uordertail);
                }
            }
        }
        return childUordertails;
    }

    /*
     * 分组统计
     */
    private List<OrderItem> statisticsToOrderItem(List<Uordertail> uordertails) {
        Map<Long, OrderItem> mapOrderItem = new HashMap<>();
        for (Uordertail uordertail : uordertails) {
            OrderItem orderItem = this.initOrderItemToUordertail(uordertail);
            if (mapOrderItem.containsKey(uordertail.getProductid())) {
                OrderItem mapitem = mapOrderItem.get(uordertail.getProductid());

                int number = mapitem.getNumber() + 1;
                float price =  mapitem.getPrice() + orderItem.getPrice();
                float memberprice = mapitem.getMemberprice() + orderItem.getMemberprice();

                mapitem.setNumber(number);
                mapitem.setPrice(price);
                mapitem.setMemberprice(memberprice);

            } else {
                mapOrderItem.put(uordertail.getProductid(), orderItem);
            }
        }
        return new ArrayList<>(mapOrderItem.values());
    }


    /**
     * 初始化 OrderItem
     *
     * @param uordertail
     * @return
     */
    private OrderItem initOrderItemToUordertail(Uordertail uordertail) {
        OrderItem orderItem = new OrderItem();
        orderItem.setImagepath(uordertail.getImagepath());
        orderItem.setIsmeal(uordertail.getIsmeal());
        orderItem.setPrice(uordertail.getPrice());
        orderItem.setMemberprice(uordertail.getMemberprice());
        orderItem.setNumber(1);
        orderItem.setName(uordertail.getName());
        orderItem.setOrderid(uordertail.getOrderid());
        orderItem.setProductid(uordertail.getProductid());
        return orderItem;
    }


}
