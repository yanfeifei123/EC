package com.yff.ecbackend.users.view;

import lombok.Data;

/**
 * 订单依赖关系配置
 */
@Data
public class OrderSettiing {
    private String tradeno;//商户单号
    private int member; //是否会员
    private float firstorder; //首单用户
    private float psfcost; //配送费
}
