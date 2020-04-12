package com.yff.ecbackend;


import com.yff.core.safetysupport.aes.Aes;
import com.yff.core.util.ToolUtil;

public class Main {

    public static void main(String[] args)    {

        String content = "Aaa!1234";

        System.out.println(ToolUtil.MD5(content));


    }
}
