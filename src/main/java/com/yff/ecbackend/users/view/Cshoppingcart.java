package com.yff.ecbackend.users.view;


import lombok.Data;

import java.util.*;

@Data
public class Cshoppingcart {

    List<Child> shoppingcart = new ArrayList<Child>();
    private int index;
    private int parentIndex;
    private float totalprice;
    private float memberprice;

}
