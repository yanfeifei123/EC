package com.yff.wechat.impl;


import com.yff.wechat.wxpaysdk.IWXPayDomain;
import com.yff.wechat.wxpaysdk.WXPayConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.security.KeyStore;


public class WXPayConfigImpl extends WXPayConfig {

    private String appID;
    private String mchID; //商户号
    private String key; //密钥key

    private byte[] certData;

    public WXPayConfigImpl() throws Exception {

    }


    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected String getAppID() {
        return this.appID;
    }

    @Override
    protected String getMchID() {
        return this.mchID;
    }

    @Override
    protected String getKey() {
        return this.key;
    }

    @Override
    protected InputStream getCertStream() {
        ClassPathResource cl = new ClassPathResource("apiclient_cert.p12");
        try {
            return cl.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo("api.mch.weixin.qq.com", false);
            }
        };
    }

}
