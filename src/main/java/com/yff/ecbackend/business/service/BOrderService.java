package com.yff.ecbackend.business.service;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.view.OrderList;
import com.yff.ecbackend.business.view.OrderSummary;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import com.yff.ecbackend.messagequeue.service.OrderMessageThreadingService;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UaddressRepository;
import com.yff.ecbackend.users.repository.UorderRepository;
import com.yff.ecbackend.users.service.UordertailService;
import com.yff.ecbackend.users.service.UserService;
import com.yff.ecbackend.users.view.OrderItem;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商家订单查询统计服务
 */

@Service
public class BOrderService {

    @PersistenceContext(unitName = "entityManagerFactoryPrimary")
    private EntityManager entityManager;

    @Autowired
    private OrderMessageThreadingService orderMessageThreadingService;

    @Autowired
    private UorderRepository uorderRepository;

    @Autowired
    private UordertailService uordertailService;

    @Autowired
    private UaddressRepository uaddressRepository;

    @Autowired
    private UserService userService;

    /**
     * 通过商家分店id查询现在单量和金额
     *
     * @param branchid
     * @return
     */
    public OrderSummary findByOrderSummary(String branchid, String orderSummaryitem) {
        String datetime = "";
        if (orderSummaryitem.equals("1")) {
            datetime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        } else if (orderSummaryitem.equals("2")) {
            datetime = DateUtil.calculationTime(new Date(), "DATE", -1);
        }
//        System.out.println(datetime);
        if (orderSummaryitem.equals("1") || orderSummaryitem.equals("2")) {
            return this.findByOrderSummaryTodayyesterday(branchid, datetime);
        } else {
            if (orderSummaryitem.equals("3")) {
                String etime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
                String stime = DateUtil.calculationTime(new Date(), "DATE", -6);
                stime = DateUtil.format(DateUtil.parseTime(stime), "yyyy-MM-dd");
//                System.out.println(stime+"   "+etime);
                return this.findByOrderSummaryRange(branchid, stime, etime);
            } else if (orderSummaryitem.equals("4")) {
                String etime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
                String stime = DateUtil.calculationTime(new Date(), "MONTH", -1);
                stime = DateUtil.format(DateUtil.parseTime(stime), "yyyy-MM-dd");
//                System.out.println(stime+"   "+etime);
                return this.findByOrderSummaryRange(branchid, stime, etime);
            }
        }
        return null;
    }

    /**
     * 查询今天昨天收入
     *
     * @param branchid
     * @param datetime
     * @return
     */
    public OrderSummary findByOrderSummaryTodayyesterday(String branchid, String datetime) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT sum(a.totalfee) totalfee, count(a.branchid) ordernum, 0.0 refundamount,  0 refundformnum  FROM u_order a where a.branchid=:branchid and  DATE_FORMAT(a.buildtime  ,'%Y-%m-%d') = DATE_FORMAT(:datetime  ,'%Y-%m-%d')");
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("branchid", branchid);
        query.setParameter("datetime", datetime);
        Map<String, Object> map = (Map<String, Object>) query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getSingleResult();
        return JSON.parseObject(JSON.toJSONString(map), OrderSummary.class);
    }


    public OrderSummary findByOrderSummaryRange(String branchid, String startdate, String enddate) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT sum(a.totalfee) totalfee, count(a.branchid) ordernum, 0.0 refundamount,  0 refundformnum  FROM u_order a where a.branchid=:branchid");
        dataSql.append(" and  DATE_FORMAT(a.buildtime  ,'%Y-%m-%d')  >=:startdate and DATE_FORMAT(a.buildtime  ,'%Y-%m-%d') <=:enddate");
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("branchid", branchid);
        query.setParameter("startdate", startdate);
        query.setParameter("enddate", enddate);
        Map<String, Object> map = (Map<String, Object>) query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getSingleResult();
        return JSON.parseObject(JSON.toJSONString(map), OrderSummary.class);
    }


    public int findByIsNotOrderComplete(String branchid) {
        return this.uorderRepository.findByIsNotOrderComplete(Long.valueOf(branchid));
    }

    public Object listenerNewOrder(String branchid) {
        OrderMessageTemplate orderMessageTemplate = this.orderMessageThreadingService.take();
        if (ToolUtil.isNotEmpty(orderMessageTemplate)) {
            if (Long.valueOf(branchid).equals(orderMessageTemplate.getBranchid())) {
                System.out.println("监听到订单" + orderMessageTemplate.getOrderid() + "  " + orderMessageTemplate.getOpenid() + "   " + orderMessageTemplate.getBranchid());
                return orderMessageTemplate;
            }
        } else {
            System.out.println("未监听到订单");
        }

        return orderMessageTemplate;
    }


    public Object findByBranchOrder(HttpServletRequest request, String branchid) {
        List<OrderList> orderViewLists = new ArrayList<>();
        List<Uorder> uorderList = this.uorderRepository.findByBranchOrder(Long.valueOf(branchid));
        for (Uorder uorder : uorderList) {

            OrderList orderList = new OrderList();
            orderList.setOrderid(uorder.getId());
            orderList.setOrdertime(uorder.getBuildtime());
            orderList.setTotalfee(uorder.getTotalfee());
            orderList.setState(uorder.getIscomplete() == 1 ? "已完成" : "未完成");
            orderList.setUaddressid(uorder.getUaddressid());
            orderList.setType(uorder.getSelf() == 1 ? "到店自取" : "外卖配送");
            User user = this.userService.findByUserid(uorder.getOpenid());
            orderList.setAvatarurl(user.getAvatarurl());
            orderList.setIscomplete(uorder.getIscomplete());
            orderList.setTradeno(uorder.getTradeno());
//            String productNames = "";
//
//            List<Uordertail> uordertails = this.uordertailService.findByUordertail(request, uorder.getId());
//            List<OrderItem> orderItems = this.uordertailService.detailedStatisticsToOrderItem(uordertails);
//            int number = 0;
//            for (OrderItem orderItem : orderItems) {
//                number+=orderItem.getNumber();
//                if (productNames.equals("")) {
//                    productNames = orderItem.getName();
//                } else {
//                    productNames += "，" + orderItem.getName();
//                }
//                orderList.setProductNames(productNames+"等共"+number+"件");
//            }
            orderViewLists.add(orderList);
        }
        this.findByuaddress(orderViewLists);
//        String s = JSON.toJSONString(orderViewLists);
//        System.out.println(s);
        return orderViewLists;
    }


    private void findByuaddress(List<OrderList> orderViewLists) {
        List<Uaddress> uaddressList = this.uaddressRepository.findAll();
        for (OrderList orderList : orderViewLists) {
            for (Uaddress uaddress : uaddressList) {
                if (orderList.getUaddressid().equals(uaddress.getId())) {
                    orderList.setUsername(uaddress.getName() + "(" + uaddress.getGender() + ")");
                }
            }
        }
    }


}
