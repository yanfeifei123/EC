package com.yff.ecbackend.business.service;


import com.alibaba.fastjson.JSON;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.view.OrderSummary;
import com.yff.ecbackend.messagequeue.pojo.OrderMessageTemplate;
import com.yff.ecbackend.messagequeue.service.OrderMessageThreadingService;
import com.yff.ecbackend.users.repository.UorderRepository;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        }else{
            if(orderSummaryitem.equals("3")){
                String etime = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
                String stime = DateUtil.calculationTime(new Date(),"DATE",-6);
                stime=DateUtil.format(DateUtil.parseTime(stime),"yyyy-MM-dd") ;
//                System.out.println(stime+"   "+etime);
                return this.findByOrderSummaryRange(branchid,stime,etime);
            }else if(orderSummaryitem.equals("4")){
                String etime = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
                String stime = DateUtil.calculationTime(new Date(),"MONTH",-1);
                stime=DateUtil.format(DateUtil.parseTime(stime),"yyyy-MM-dd") ;
//                System.out.println(stime+"   "+etime);
                return this.findByOrderSummaryRange(branchid,stime,etime);
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



    public int findByIsNotOrderComplete(String branchid){
        return this.uorderRepository.findByIsNotOrderComplete(Long.valueOf(branchid));
    }

    public Object listenerNewOrder(String branchid){
        OrderMessageTemplate orderMessageTemplate = this.orderMessageThreadingService.take();
        if(ToolUtil.isNotEmpty(orderMessageTemplate)){
            if(Long.valueOf(branchid).equals(orderMessageTemplate.getBranchid())){
                System.out.println("监听到订单"+orderMessageTemplate.getOrderid()+"  "+orderMessageTemplate.getOpenid()+"   "+orderMessageTemplate.getBranchid());
                return orderMessageTemplate;
            }
        }else{
            System.out.println("未监听到订单");
        }
        return orderMessageTemplate;
    }




}
