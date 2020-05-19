package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.page.Paging;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
import com.yff.ecbackend.business.service.BphotoService;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UorderRepository;
import com.yff.ecbackend.users.view.OrderDetail;
import com.yff.ecbackend.users.view.OrderItem;
import com.yff.ecbackend.users.view.OrderSettiing;
import org.bouncycastle.jce.provider.asymmetric.ec.KeyFactory;
import org.hibernate.dialect.Ingres9Dialect;
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

    @Autowired
    private UorderOdrService uorderOdrService;

    @Autowired
    private WeChatService weChatService;

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
        Bbranch bbranch = bbranchService.findOne(Long.valueOf(branchid));
        if (uorderList.size() == 0) {
            firstorder = bbranch.getFirstorder();
        }
        psfcost = bbranch.getPsfcost();
        OrderSettiing orderSettiing = new OrderSettiing();
        orderSettiing.setTradeno(tradeno);
        orderSettiing.setMember(Integer.parseInt(user.getMember()));
        orderSettiing.setPsfcost(psfcost);
        orderSettiing.setFirstorder(firstorder);
        return orderSettiing;
    }


    public List<Uorder> findUserIsfirstorder(String openid) {
        return this.uorderRepository.findUserIsfirstorder(openid);
    }


    @Transactional(rollbackFor = Exception.class)
    public Object updateUserOrder(String orderid, String tradeno, String openid, String shoppingcart, String bid, String totalfee, String branchid, String isself, String discount, String uaddressid, String firstorder, String ismember) {
        System.out.println("openid:" + openid + " bid:" + bid + " totalfee:" + totalfee + " branchid:" + branchid + "  isself:" + isself + "  discount:" + discount + " uaddressid:" + uaddressid + " firstorder:" + firstorder + " ismember:" + ismember);
        System.out.println("shoppingcart:"+shoppingcart);

        User user = this.userService.getUser(openid);
        Uorder uorder = this.updateUorder(orderid, tradeno, user, bid, branchid, Integer.parseInt(isself), Float.valueOf(totalfee), Float.valueOf(discount), shoppingcart, uaddressid, firstorder, ismember);
        this.uordertailService.updateUordertail(uorder.getId(), shoppingcart);

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
        for(Uorder uorder : uorderList){
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
    private Uorder updateUorder(String orderid, String tradeno, User user, String bid, String branchid, int isself, float totalfee, float discount, String josn, String uaddressid, String firstorder, String ismember) {

        int odr = this.uorderOdrService.findByUorderOdr(Long.valueOf(branchid));
        Uorder uorder = null;
        if (ToolUtil.isNotEmpty(orderid)) {
            uorder = this.uorderOdrService.findOne(Long.valueOf(orderid));
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
        uorder.setJson(josn);
//        uorder.setTradeno(tradeno);
        uorder.setUaddressid(Long.valueOf(uaddressid));
        uorder.setFirstorder(Float.parseFloat(firstorder));
        uorder.setIsmember(Integer.parseInt(ismember));

        Uaddress uaddress = uaddressService.findOne(Long.valueOf(uaddressid));
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
    public List<Uorder> findOrderList(HttpServletRequest request, String openid, String pageNum, String pageSize) {
        List<Uorder> uorders = this.uorderRepository.findUserOrderpage(openid, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        setbranchName(uorders, bbranchService.findAll());
        List<OrderItem> orderItems = this.findByOrderItem(request, openid);
        this.setOrderItem(uorders, orderItems);
//        System.out.println(pageNum + "  " + pageSize + "  ListSize:" + uorders.size());
//        String s = JSON.toJSONString(uorders);
//        System.out.println("findOrderList:"+s);
        return uorders;
    }


    private void setOrderItem(List<Uorder> uorders, List<OrderItem> orderItems) {
        for (Uorder uorder : uorders) {
            int total = uorder.getTotal();
            for (OrderItem orderItem : orderItems) {
                if (uorder.getId().equals(orderItem.getOrderid())) {
                    uorder.getOrderItems().add(orderItem);
                    total += orderItem.getNumber();
                    uorder.setTotal(total);
                    if (uorder.getIscomplete() == 0) {
                        if (uorder.getSelf() == 1) {
                            uorder.setInfo("到店自取");
                        } else {
                            uorder.setInfo("商家已接单");
                        }
                    } else {
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
//        System.out.println("用户端订单统计："+list.size());
        List<OrderItem> orderItems = JSON.parseArray(JSON.toJSONString(list), OrderItem.class);
        this.bphotoService.setOrderItemImagePath(request, orderItems);
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
    public Object findOrderDetailed(HttpServletRequest request, String orderid) {

        OrderDetail orderDetail = new OrderDetail();

        Uorder uorder = this.findOne(Long.valueOf(orderid));
        if (uorder.getIscomplete() == 0) { //未完成订单
            String e = weChatService.timeCalculation(uorder.getBuildtime());
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
        orderDetail.setOrder(uorder.getOdr());
        orderDetail.setAddress(uorder.getAddress());
        orderDetail.setReceiver(uorder.getReceiver());
        orderDetail.setPhone(uorder.getPhone());

        orderDetail.setPaym("在线支付");

        if (uorder.getSelf() == 1) {
            orderDetail.setExptimeinf(bbranch.getDetailed() + "," + bbranch.getName());
            orderDetail.setExptime("马上完成");
            orderDetail.setDisservice("到店自取");
        }
        orderDetail.setSelf(uorder.getSelf());

        orderDetail.setCompletetime(uorder.getCompletetime());
        orderDetail.setOrdertime(uorder.getBuildtime());


        orderDetail.setOrderno(uorder.getTradeno());

        List<Uordertail> uordertails = this.uordertailService.findByUordertail(request, Long.valueOf(orderid));
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
