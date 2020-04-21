package com.yff.ecbackend.users.view;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class OrderItem {

    private Long productid;
    private String name;
    private Integer number;
    private Float price;
    private Float memberprice;
    private Long orderid;
    private Integer ismeal;
    private String imagepath;

}
