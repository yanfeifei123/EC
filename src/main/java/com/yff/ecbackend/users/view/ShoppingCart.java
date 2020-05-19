package com.yff.ecbackend.users.view;


import lombok.Data;
import java.util.*;
@Data
public class ShoppingCart {
    private long id;
    private int num;
    private String name;
    private float tprice;
    private float tmemberprice;
    private int index;
    private int parentIndex;
    private List<Cshoppingcart>  items  =new ArrayList<>();

}
