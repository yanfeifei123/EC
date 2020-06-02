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
import com.yff.ecbackend.users.view.Child;
import com.yff.ecbackend.users.view.Cshoppingcart;
import com.yff.ecbackend.users.view.OrderItem;
import com.yff.ecbackend.users.view.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    public void clearUordertail(Long orderid) {
        this.uordertailRepository.clearUordertail(Long.valueOf(orderid));
    }

    /**
     * 生成订单明细
     * @param orderid
     * @param shoppingCarts
     * @return
     */
    public List<Uordertail> updateUordertail(Long orderid, List<ShoppingCart> shoppingCarts) {
        this.clearUordertail(orderid);
        List<Uordertail> uordertails = new ArrayList<>();
        int i = 0;
        for (ShoppingCart shoppingcart : shoppingCarts) {

            Long productid = shoppingcart.getId();
            Integer num = shoppingcart.getNum();
            List<Cshoppingcart> items = shoppingcart.getItems();

            if (num != 1) {  // 多个商品同一类型
                for (int j = 1; j <= num; j++) {

                    Bproduct bproduct = bproductService.findOne(productid);
                    int ismeal = items.size() != 0 ? 1 : 0;

                    Uordertail uordertail = this.assembleUordertail(orderid, bproduct.getMemberprice(), bproduct.getPrice(), bproduct.getId(), j, null, ismeal,bproduct.getName(),bproduct.getImagepath());
                    uordertail = this.uordertailRepository.save(uordertail);

                    if (ismeal == 1) {  //如果包含套餐
                        List<Child> childshoppingcarts = items.get(j - 1).getShoppingcart();
                        for (int k = 0; k < childshoppingcarts.size(); k++) {
                            Child childshoppingcart = childshoppingcarts.get(k);
                            Integer number = childshoppingcart.getNum(); //数量
                            Long productid1 = childshoppingcart.getId();  //商品id
                            for (int l = 1; l <= number; l++) {
                                Bproduct bproductc = bproductService.findOne(productid1);
                                Uordertail uordertailc = this.assembleUordertail(orderid, bproductc.getMemberprice(), bproductc.getPrice(), bproductc.getId(), l, uordertail.getId(), 0,bproductc.getName(),bproductc.getImagepath());
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
                    Uordertail uordertail = this.assembleUordertail(orderid, tmemberprice, tprice, productid, i + 1, null, ismeal,bproduct.getName(),bproduct.getImagepath());
                    uordertail = this.uordertailRepository.save(uordertail);
//                    System.out.println("pid:" + uordertail.getId());
                    List<Child> childshoppingcarts = items.get(0).getShoppingcart();
                    for (int k = 0; k < childshoppingcarts.size(); k++) {
                        Child childshoppingcart = childshoppingcarts.get(k);
                        Integer number = childshoppingcart.getNum(); //数量
                        Long productid1 = childshoppingcart.getId();  //商品id
                        for (int l = 1; l <= number; l++) {
                            Bproduct bproductc = bproductService.findOne(productid1);
                            Uordertail uordertailc = this.assembleUordertail(orderid, bproductc.getMemberprice(), bproductc.getPrice(), bproductc.getId(), l, uordertail.getId(), 0,bproductc.getName(),bproductc.getImagepath());
                            this.uordertailRepository.save(uordertailc);
                        }
                    }
                } else {
                    Uordertail uordertail = this.assembleUordertail(orderid, tmemberprice, tprice, productid, i + 1, null, 0,bproduct.getName(),bproduct.getImagepath());
                    uordertails.add(uordertail);
                }
            }
            i++;
        }
        return this.update(uordertails);
    }


    public Uordertail assembleUordertail(Long orderid, float memberprice, float price, Long productid, int odr, Long pid, int ismeal,String name,String url) {
        Uordertail uordertail = new Uordertail();
        uordertail.setBuildtime(new Date());
        uordertail.setOrderid(orderid);
        uordertail.setMemberprice(memberprice);
        uordertail.setPrice(price);
        uordertail.setProductid(productid);
        uordertail.setOdr(odr);
        uordertail.setIsmeal(ismeal);
        uordertail.setPid(pid);
        uordertail.setName(name);
        uordertail.setUrl(url);
        return uordertail;
    }

    /**
     * 查询订单明细
     *
     * @param orderid
     * @return
     */
    public List<Uordertail> findByUordertail( Long orderid) {
        List<Uordertail> uordertails = this.uordertailRepository.findByUordertail(orderid);
        return uordertails;
    }

    /**
     * 已经查询好的List<Uordertail>
     *
     * @param uordertails
     * @return
     */
    public List<OrderItem> detailedStatisticsToOrderItem(List<Uordertail> uordertails) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        boolean f = false;
        for (Uordertail uordertail : uordertails) {
            OrderItem orderItem = this.initOrderItemToUordertail(uordertail);
            if (uordertail.getIsmeal() == 1) {  //套餐
                List<Uordertail> childUordertails = findByChildUordertail(uordertail, uordertails);
                List<OrderItem> childOrderItem = this.statisticsToOrderItem(childUordertails);
                orderItem.setOrderItems(childOrderItem);
                orderItems.add(orderItem);
                f = true;
            }
        }
        if (f) {
            for (OrderItem orderItem : orderItems) {
                uordertails.removeIf(node -> node.getProductid().equals(orderItem.getProductid()));
                for (OrderItem child : orderItem.getOrderItems()) {
                    uordertails.removeIf(node -> node.getProductid().equals(child.getProductid()));
                }
            }
            List<OrderItem> orderItems1 = this.statisticsToOrderItem(uordertails);
            orderItems.addAll(orderItems1);
        } else {
            List<OrderItem> orderItemList = this.statisticsToOrderItem(uordertails);
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
                float price = mapitem.getPrice() + orderItem.getPrice();
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
        orderItem.setImagepath(uordertail.getUrl());
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
