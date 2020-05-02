package com.yff.ecbackend;


import com.yff.core.util.DateUtil;

import java.util.Date;

public class Main {

    public static void main(String[] args)    {
       String stime = DateUtil.calculationTime(new Date(),"MONTH",-1);
       System.out.println(stime);
    }
}
