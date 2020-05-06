package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.DateUtil;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
import com.yff.ecbackend.business.service.BphotoService;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UorderRepository;
import com.yff.ecbackend.users.view.OrderDetail;
import com.yff.ecbackend.users.view.OrderItem;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
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

    @Autowired
    private BphotoService bphotoService;

    @Autowired
    private UaddressService uaddressService;

    private DecimalFormat df = new DecimalFormat("#.00");


    public List<Uorder> findUserOrder(String openid) {
        return this.uorderRepository.findUserOrder(openid);
    }


    @Transactional(rollbackFor = Exception.class)
    public Object updateUserOrder(String openid, String shoppingcart, String bid, String totalfee, String branchid, String isself, String discount, String out_trade_no, String uaddressid, String firstorder, String ismember) {
//        System.out.println(shoppingcart);
        User user = this.userService.getUser(openid);
        Uorder uorder = this.updateUorder(user, bid, branchid, Integer.parseInt(isself), Float.valueOf(totalfee), Float.valueOf(discount), shoppingcart, out_trade_no, uaddressid, firstorder, ismember);
        this.uordertailService.updateUordertail(uorder.getId(), shoppingcart);
        return uorder;
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
    private Uorder updateUorder(User user, String bid, String branchid, int isself, float totalfee, float discount, String josn, String tradeno, String uaddressid, String firstorder, String ismember) {
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
        uorder.setTradeno(tradeno);
        uorder.setUaddressid(Long.valueOf(uaddressid));
        uorder.setFirstorder(Float.parseFloat(firstorder));
        uorder.setIsmember(Integer.parseInt(ismember));

        return this.uorderRepository.save(uorder);
    }

    /**
     * 查询订单列表
     *
     * @param openid
     * @return
     */
    public List<Uorder> findOrderList(HttpServletRequest request, String openid) {
        System.out.println(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        List<Uorder> uorders = this.uorderRepository.findUserOrder(openid);
        setbranchName(uorders, bbranchService.findAll());
        List<OrderItem> orderItems = this.findByOrderItem(request, openid);
        this.setOrderItem(uorders, orderItems);
        System.out.println(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return uorders;
    }


    private void setOrderItem(List<Uorder> uorders, List<OrderItem> orderItems) {
        for (Uorder uorder : uorders) {
            int total = uorder.getTotal();
            for (OrderItem orderItem : orderItems) {
                if (uorder.getId() == orderItem.getOrderid()) {
                    uorder.getOrderItems().add(orderItem);
                    total += orderItem.getNumber();
                    uorder.setTotal(total);
                    if(uorder.getIscomplete()==0){
                        if (uorder.getSelf() == 1) {
                            uorder.setInfo("到店自取");
                        } else {
                            uorder.setInfo("商家已接单");
                        }
                    }else{
                        uorder.setInfo("已完成");
                    }
                }
            }
        }
    }

    /**
     * 通过用户openid 查询
     *
     * @param request
     * @param openid
     * @return
     */
    public List<OrderItem> findByOrderItem(HttpServletRequest request, String openid) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("select  a.productid,  if(a.ismeal=1,concat(c.name,'(套餐)'),c.name )  name, count(a.productid)   number ,sum(a.price) price  ,sum(a.memberprice ) memberprice ,a.ismeal , a.orderid   FROM u_orderta a,u_order b ,b_product c where a.orderid=b.id  and c.id=a.productid  and a.pid is null AND b.openid=:openid GROUP BY a.productid,c.name,a.ismeal,a.orderid ORDER BY b.buildtime desc");
//        System.out.println(dataSql.toString());
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("openid", openid);
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<OrderItem> orderItems = JSON.parseArray(JSON.toJSONString(list), OrderItem.class);
        this.bphotoService.setOrderItemImagePath(request, orderItems);
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

    /**
     * 订单详情
     *
     * @param orderid
     * @return
     */
    public Object findOrderDetailed(HttpServletRequest request, String orderid) {
        OrderDetail orderDetail = new OrderDetail();

        Uorder uorder = this.findOne(Long.valueOf(orderid));
        if (uorder.getIscomplete() == 0) { //未完成订单
            String e = this.timeCalculation(uorder.getBuildtime());
            orderDetail.setHour(e);
        }
        orderDetail.setIscomplete(uorder.getIscomplete());
        orderDetail.setOpenid(uorder.getOpenid());
        orderDetail.setOrderid(uorder.getId());
        Bbranch bbranch = bbranchService.findOne(uorder.getBranchid());
        orderDetail.setBranchid(uorder.getBranchid());
        orderDetail.setBranchname(bbranch.getName());
        orderDetail.setDeliveryfee(bbranch.getPsfcost());
        orderDetail.setTotalfee(uorder.getTotalfee());
        orderDetail.setFirstorder(uorder.getFirstorder());
        orderDetail.setIsmember(uorder.getIsmember());
        orderDetail.setDiscount(uorder.getDiscount());
        orderDetail.setPaym("在线支付");

        if (uorder.getSelf() == 1) {
            orderDetail.setExptimeinf( bbranch.getDetailed()+","+ bbranch.getName()  );
            orderDetail.setExptime("马上完成");
            orderDetail.setDisservice("到店自取");
        }
        orderDetail.setSelf(uorder.getSelf());

        orderDetail.setCompletetime(uorder.getCompletetime());
        orderDetail.setOrdertime(uorder.getBuildtime());
        if (orderDetail.getSelf() == 0) {
            Uaddress uaddress = this.uaddressService.findOne(uorder.getUaddressid());
            orderDetail.setUaddress(uaddress);
        }


        orderDetail.setOrderno(uorder.getTradeno());

        List<Uordertail> uordertails = this.uordertailService.findByUordertail(request, Long.valueOf(orderid));
        List<OrderItem> orderItems = this.uordertailService.detailedStatisticsToOrderItem(uordertails);
        orderDetail.setOrderItems(orderItems);

//        String s = JSON.toJSONString(orderDetail) ;
//        System.out.println(s);
        return orderDetail;
    }

    /**
     * 预计订单完成时间
     *
     * @param time
     * @return
     */
    public String timeCalculation(Date time) {
        String  date = DateUtil.calculationTime(time,"MINUTE",15);
        return DateUtil.format(DateUtil.parseTime(date),"HH:mm") ;
    }



    public List<OrderItem> orderByOrderDetailed(HttpServletRequest request, String orderid) {
        List<Uordertail> uordertails = this.uordertailService.findByUordertail(request, Long.valueOf(orderid));
        List<OrderItem> orderItems = this.uordertailService.detailedStatisticsToOrderItem(uordertails);
        return orderItems;
    }

    /**
     * 配合消息模板
     *
     * @return
     */
    public Map<String, TemplateData> findByOrderSendTempInfo(HttpServletRequest request, String orderid) {
        Uorder uorder = this.findOne(Long.valueOf(orderid));
        Map<String, TemplateData> map = new HashMap<>();
        map.put("character_string1", new TemplateData(uorder.getTradeno()));
        String orderType = uorder.getSelf() == 1 ? "到店自取" : "外卖配送";
        map.put("phrase2", new TemplateData(orderType));
        map.put("amount4", new TemplateData(df.format(uorder.getTotalfee())));
        List<OrderItem> orderItems = this.orderByOrderDetailed(request, orderid);
        String names = "";
        String istc = "";
        for (OrderItem orderItem : orderItems) {
            istc = orderItem.getIsmeal() == 1 ? "（套餐）" : "";
            if (names.equals("")) {
                names = orderItem.getName() + istc;
            } else {
                names += "," + orderItem.getName() + istc;
            }
        }
        map.put("thing6", new TemplateData(names));
        Uaddress uaddress = this.uaddressService.findOne(uorder.getUaddressid());
        map.put("name7", new TemplateData(uaddress.getName() + "(" + uaddress.getGender() + ")"));
        return map;
    }

}
