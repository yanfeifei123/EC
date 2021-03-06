package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.page.Paging;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
import com.yff.ecbackend.business.service.BphotoService;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.*;
import com.yff.ecbackend.users.repository.UorderRepository;
import com.yff.ecbackend.users.view.OrderBean;
import com.yff.ecbackend.users.view.OrderDetail;
import com.yff.ecbackend.users.view.OrderItem;
import com.yff.ecbackend.users.view.OrderSettiing;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @Autowired
    private UorderOdrService uorderOdrService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private MessageStackService messageStackService;

    @Autowired
    private UorderrService uorderrService;

    private DecimalFormat df = new DecimalFormat("#.00");

    /**
     * 订单设置项
     *
     * @param openid
     * @param branchid
     * @return
     */
    public OrderSettiing queryOrderSettiing(String openid, String branchid) {
        String tradeno = this.weChatService.createOutTradeno();
        User user = this.userService.findByUserid(openid);
        List<Uorder> uorderList = this.findUserIsfirstorder(openid);
        float firstorder = 0;
        float psfcost = 0;
        int isps = 0;
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(branchid));
        if (uorderList.size() == 0) {
            firstorder = bbranch.getFirstorder();
        }
        isps = bbranch.getIsps();
        psfcost = bbranch.getPsfcost();
        OrderSettiing orderSettiing = new OrderSettiing();
        orderSettiing.setTradeno(tradeno);
        orderSettiing.setMember(user.getMember());
        orderSettiing.setPsfcost(psfcost);
        orderSettiing.setFirstorder(firstorder);
        orderSettiing.setIsps(isps);
        return orderSettiing;
    }


    public List<Uorder> findUserIsfirstorder(String openid) {
        return this.uorderRepository.findUserIsfirstorder(openid);
    }

    /**
     * 创建订购单信息
     *
     * @param orderBean
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object updateUserOrder(OrderBean orderBean) {

        User user = this.userService.getUser(orderBean.getOpenid());

        Uorder uorder = this.updateUorder(orderBean.getOrderid(), orderBean.getTradeno(), user, orderBean.getBid(), orderBean.getBranchid(), orderBean.getIsself(), Float.valueOf(orderBean.getTotalfee()), Float.valueOf(orderBean.getDiscount()), orderBean.getUaddressid(), orderBean.getFirstorder(), orderBean.getIsmember());
        this.uordertailService.updateUordertail(uorder.getId(), orderBean.getShoppingCart());
        return uorder;
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteOrder(String orderid) {
        if (ToolUtil.isNotEmpty(orderid)) {
            Uorder uorder = this.findOne(Long.valueOf(orderid));
            if (uorder.getStatus() == 0) {
                this.delete(Long.valueOf(orderid));
                this.uordertailService.clearUordertail(Long.valueOf(orderid));
            }
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int clearMyorder(String openid) {
        List<Uorder> uorderList = this.uorderRepository.findUserUnpaidorder(openid);
//        System.out.println(JSON.toJSONString(uorderList));
        for (Uorder uorder : uorderList) {
            this.uordertailService.clearUordertail(uorder.getId());
        }
        this.uorderRepository.clearMyorder(openid);
        return 1;
    }

    /**
     * 初始化订单信息未支付状态
     *
     * @param user
     * @param bid
     * @param branchid
     * @param isself
     * @param totalfee
     * @return
     */
    private Uorder updateUorder(Long orderid, String tradeno, User user, Long bid, Long branchid, int isself, float totalfee, float discount, Long uaddressid, float firstorder, String ismember) {
        int odr = this.uorderOdrService.findByUorderOdr(branchid);
        Uorder uorder = null;
        if (ToolUtil.isNotEmpty(orderid)) {
            uorder = this.uorderOdrService.findOne(orderid);
        } else {
            uorder = new Uorder();
        }
        uorder.setTradeno(tradeno);  //商户订单号
        uorder.setBid(Long.valueOf(bid));
        uorder.setBranchid(Long.valueOf(branchid));
        uorder.setIscomplete(0);
        uorder.setOpenid(user.getOpenid());
        uorder.setStatus(0);
        uorder.setTotalfee(totalfee);
        uorder.setUserid(user.getId());
        uorder.setOdr(odr);
        uorder.setBuildtime(new Date());
        uorder.setSelf(isself);
        uorder.setDiscount(discount);
        uorder.setIscomplete(0);
        uorder.setUaddressid(uaddressid);
        uorder.setFirstorder(firstorder);
        uorder.setIsmember(Integer.parseInt(ismember));
        Uaddress uaddress = uaddressService.findOne(uaddressid);

        uorder.setAddress(uaddress.getArea() + uaddress.getDetailed());
        uorder.setReceiver(uaddress.getName() + "（" + uaddress.getGender() + "）");
        uorder.setPhone(uaddress.getPhone());

        return this.uorderRepository.save(uorder);
    }

    /*
      用户订单总页数
     */
    public int countAllByUorderOAndOpenid(String openid, String pageSize) {
        int totalRecord = this.uorderRepository.countAllByUorderOAndOpenid(openid);
        Paging paging = new Paging();
        paging.setPagesize(Integer.parseInt(pageSize));
        paging.setTotalRecord(totalRecord);
        return paging.getTotalPage();
    }

    /**
     * 查询用户订单列表
     *
     * @param openid
     * @return
     */
    public List<Uorder> findOrderList(String openid, String pageNum, String pageSize) {
        List<Uorder> uorders = this.uorderRepository.findUserOrderpage(openid, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        setbranchName(uorders, bbranchService.findAll());
        this.setOrderItem(uorders);

        return uorders;
    }


    private void setOrderItem(List<Uorder> uorders) {
        for (Uorder uorder : uorders) {
            int total = uorder.getTotal();
            List<OrderItem> orderItems = this.findByOrderItem(uorder.getOpenid(), uorder.getId());
            uorder.setOrderItems(orderItems);
            for (OrderItem orderItem : orderItems) {
                total += orderItem.getNumber();
            }
            uorder.setTotal(total);

            String info = this.uorderrService.findRefundInfo(uorder.getId());

            if (ToolUtil.isNotEmpty(info)) {
                uorder.setInfo(info);
            } else {
                if (uorder.getIscomplete() == 0) {
                    if (uorder.getStatus() == 0) {
                        uorder.setInfo("未支付");
                    } else {
                        if (uorder.getSelf() == 1) {
                            uorder.setInfo("到店自取");
                        } else {
                            uorder.setInfo("商家已接单");
                        }
                    }
                } else {
                    uorder.setInfo("已完成");
                }
            }


        }
    }

    /**
     * 通过用户openid 查询
     *
     * @param openid
     * @return
     */
    public List<OrderItem> findByOrderItem(String openid, Long orderid) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("select ");
        dataSql.append(" a.productid,  a.name, count(a.productid)   number ,sum(a.price) price  ,sum(a.memberprice ) memberprice ,a.ismeal , a.orderid  ,a.url  imagepath ");
        dataSql.append("FROM u_orderta a,u_order b  ");
        dataSql.append(" where a.orderid=b.id   and a.pid is null AND b.openid=:openid and a.orderid=:orderid ");
        dataSql.append(" GROUP BY a.productid,a.name,a.ismeal,a.orderid ,a.url ");
        dataSql.append(" ORDER BY b.buildtime desc ");
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("openid", openid);
        query.setParameter("orderid", orderid);
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<OrderItem> orderItems = JSON.parseArray(JSON.toJSONString(list), OrderItem.class);
        this.bphotoService.setOrderItemImagePath(orderItems);
        return orderItems;
    }


    private void setbranchName(List<Uorder> uorders, List<Bbranch> bbranchs) {
        for (Uorder uorder : uorders) {
            for (Bbranch bbranch : bbranchs) {
                if (uorder.getBranchid().equals(bbranch.getId())) {
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
    public Object findOrderDetailed(String orderid) {

        OrderDetail orderDetail = new OrderDetail();

        Uorder uorder = this.findOne(Long.valueOf(orderid));
        String e = weChatService.timeCalculation(uorder.getBuildtime());
        orderDetail.setHour(e);
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
        orderDetail.setOrder(uorder.getOdr());
        orderDetail.setAddress(uorder.getAddress());
        orderDetail.setReceiver(uorder.getReceiver());
        orderDetail.setPhone(uorder.getPhone());

        String info = this.uorderrService.findRefundInfo(uorder.getId());
        if (ToolUtil.isNotEmpty(info)) {
            orderDetail.setTitlestatus(info);
        } else {
            if (uorder.getIscomplete() == 1) {
                orderDetail.setTitlestatus("订单已完成");
            } else if (uorder.getIscomplete() == 0 && uorder.getSelf() == 0) {
                orderDetail.setTitlestatus("预计送达");
            } else if (uorder.getIscomplete() == 0 && uorder.getSelf() == 1) {
                orderDetail.setTitlestatus("到店自取");
            }
        }

        orderDetail.setPaym(uorder.getPaymode() == 0 ? "微信支付" : "会员支付");

        if (uorder.getSelf() == 1) {
            orderDetail.setExptimeinf(bbranch.getArea() + "," + bbranch.getDetailed());
            orderDetail.setExptime("马上完成");
            orderDetail.setDisservice("到店自取");
        }
        orderDetail.setSelf(uorder.getSelf());

        orderDetail.setCompletetime(uorder.getCompletetime());
        orderDetail.setOrdertime(uorder.getBuildtime());


        orderDetail.setOrderno(uorder.getTradeno());

        List<Uordertail> uordertails = this.uordertailService.findByUordertail(Long.valueOf(orderid));
        List<OrderItem> orderItems = this.uordertailService.detailedStatisticsToOrderItem(uordertails);
        orderDetail.setOrderItems(orderItems);
        int totalnum = this.totalnum(orderItems);
        orderDetail.setTotalnum(totalnum);

//        String s = JSON.toJSONString(orderDetail) ;
//        System.out.println(s);

        return orderDetail;
    }

    private int totalnum(List<OrderItem> orderItems) {
        int num = 0;
        for (OrderItem orderItem : orderItems) {
            num += orderItem.getNumber();
            for (OrderItem child : orderItem.getOrderItems()) {
                num += child.getNumber();
            }
        }
        return num;
    }


    public List<OrderItem> orderByOrderDetailed(String orderid) {
        List<Uordertail> uordertails = this.uordertailService.findByUordertail(Long.valueOf(orderid));
        List<OrderItem> orderItems = this.uordertailService.detailedStatisticsToOrderItem(uordertails);
        return orderItems;
    }



    /**
     * 通过商户号查询订单
     *
     * @param tradeno
     * @return
     */
    public Uorder findTradenoUorder(String tradeno) {
        return this.uorderRepository.findTradenoUorder(tradeno);
    }

    /**
     * 更新订单状态
     *
     * @param tradeno
     * @return
     */

    public Uorder updateUorder(String tradeno) {
        Uorder uorder = this.findTradenoUorder(tradeno);
        uorder.setStatus(1);

        this.clearMyorder(uorder.getOpenid());  //清空未支付的订单

        return uorder = this.update(uorder);  //更新订单状态
    }

    public Uorder updateUorderComplete(String orderid) {
        Uorder uorder = this.findOne(Long.valueOf(orderid));
        uorder.setIscomplete(1);
        uorder.setCompletetime(new Date());
        return this.update(uorder);
    }

    /**
     * 更新订单地址并且把消息推送给商家
     *
     * @param orderid
     * @param uaddressid
     * @return
     */
    public Uorder updateAddress(String orderid, String uaddressid) {
        Uorder uorder = this.findOne(Long.valueOf(orderid));
        Uaddress uaddress = this.uaddressService.selectAddress(uaddressid);

        uorder.setAddress(uaddress.getArea() + uaddress.getDetailed());
        uorder.setReceiver(uaddress.getName() + "（" + uaddress.getGender() + "）");
        uorder.setPhone(uaddress.getPhone());
        if (uorder.getStatus() == 1) {
            String type = "updateAddress";
            this.messageStackService.doOrderTask(uorder.getBranchid(), uorder.getOpenid(), uorder.getId(), type);
        }
        return uorder;
    }

    /**
     * 查询待退款信息
     *
     * @param branchid
     * @return
     */
    public int countAllByBranchStayRefundOrder(Long branchid) {
        return this.uorderRepository.countAllByBranchStayRefundOrder(branchid);
    }

    /**
     * 退款更新操作
     *
     * @param out_trade_no
     * @param agree
     * @return
     */
    public Object refundOpt(String out_trade_no, boolean agree) {

        Uorder uorder = this.findTradenoUorder(out_trade_no);
        Uorderr uorderr = this.uorderrService.findUorderrOrderid(uorder.getId());
        if (agree) {
            uorderr.setAgree(1);
            uorder.setStatus(2);
        } else {
            uorder.setStatus(1);
        }
        uorder.setCompletetime(new Date());
        uorder.setIscomplete(1);
        uorderr.setEnd(1);
        this.update(uorder);
        this.uorderrService.update(uorderr);
        return uorder;
    }


}
