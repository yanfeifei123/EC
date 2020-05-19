package com.yff.ecbackend.users.view;

import lombok.Data;
import java.util.*;
@Data
public class OrderBean {
    private Long orderid;
    private String tradeno;
    private String openid;
    private List<ShoppingCart>  shoppingCart;
    private Long bid;
    private String totalfee;
    private Long branchid;
    private int isself;
    private String discount;
    private Long uaddressid;
    private float firstorder;
    private String ismember;
}
