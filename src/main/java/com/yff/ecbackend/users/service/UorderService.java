package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UorderRepository;
import com.yff.ecbackend.users.view.OrderItem;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service
public class UorderService extends BaseService<Uorder, Long> {

    @PersistenceContext(unitName = "entityManagerFactoryPrimary")
    private EntityManager entityManager;

    @Autowired
    private UorderRepository uorderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UordertailService uordertailService;

    @Autowired
    private BbranchService bbranchService;

    public List<Uorder> findUserOrder(String openid) {
        return this.uorderRepository.findUserOrder(openid);
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateUserOrder(String openid, String shoppingcart, String bid, String totalfee, String branchid, String isself, String discount) {
//        System.out.println(shoppingcart);
        User user = this.userService.getUser(openid);
        Uorder uorder = this.updateUorder(user, bid, branchid, Integer.parseInt(isself), Float.valueOf(totalfee), Float.valueOf(discount), shoppingcart);
        this.uordertailService.updateUordertail(uorder.getId(), shoppingcart);
        return 1;
    }


    /**
     * 创建用户订单
     *
     * @param user
     * @param bid
     * @param branchid
     * @param isself
     * @param totalfee
     * @return
     */
    private Uorder updateUorder(User user, String bid, String branchid, int isself, float totalfee, float discount, String josn) {
        Uorder uorder = new Uorder();
        uorder.setBid(Long.valueOf(bid));
        uorder.setBranchid(Long.valueOf(branchid));
        uorder.setIscomplete(0);
        uorder.setOpenid(user.getOpenid());
        uorder.setStatus(1);
        uorder.setTotalfee(totalfee);
        uorder.setUserid(user.getId());
        uorder.setOdr(1);
        uorder.setBuildtime(new Date());
        uorder.setSelf(isself);
        uorder.setDiscount(discount);
        uorder.setIscomplete(0);
        uorder.setJson(josn);
        return this.uorderRepository.save(uorder);
    }

    /**
     * 查询订单列表
     *
     * @param openid
     * @return
     */
    public List<Uorder> findOrderList(String openid) {
        List<Uorder> uorders = this.uorderRepository.findUserOrder(openid);
        setbranchName(uorders, bbranchService.findAll());
        List<OrderItem> orderItems = this.findByOrderItem(openid);
        this.setOrderItem(uorders,orderItems);
//        String s = JSON.toJSONString(uorders);
//        System.out.println(s);
        return uorders;
    }


    private void setOrderItem(List<Uorder> uorders, List<OrderItem> orderItems) {
        for (Uorder uorder : uorders) {
            for (OrderItem orderItem : orderItems) {
                if (uorder.getId() == orderItem.getOrderid()) {
                    uorder.getOrderItems().add(orderItem);
                }
            }
        }
    }

    public List<OrderItem> findByOrderItem(String openid) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("select  a.productid,  c.name, count(a.productid)   number ,sum(a.price) price  ,sum(a.memberprice ) memberprice ,a.ismeal , a.orderid   FROM u_orderta a,u_order b ,b_product c where a.orderid=b.id  and c.id=a.productid  and a.pid is null AND b.openid=:openid GROUP BY a.productid,c.name,a.ismeal,a.orderid ORDER BY b.buildtime desc");
//        System.out.println(dataSql.toString());
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("openid",openid);
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<OrderItem> orderItems = JSON.parseArray(JSON.toJSONString(list), OrderItem.class);
        return orderItems;
    }


    private void setbranchName(List<Uorder> uorders, List<Bbranch> bbranchs) {
        for (Uorder uorder : uorders) {
            for (Bbranch bbranch : bbranchs) {
                if (uorder.getBranchid() == bbranch.getId()) {
                    uorder.setBranchname(bbranch.getName());
                }
            }
        }
    }


}
