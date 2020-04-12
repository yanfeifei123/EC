package com.yff.core.safetysupport.parameterconf;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@ConfigurationProperties(prefix = "parameterconf")
@Component
public class Parameterconf {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
    private String appid;
    private String appsecret;
    private String sessionHost;
    private String bnumber;
    private String notify_url;

    private String paykey;

    private String unifiedorderurl;

//    public String getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
//    }
//
//    public String getBase64Secret() {
//        return base64Secret;
//    }
//
//    public void setBase64Secret(String base64Secret) {
//        this.base64Secret = base64Secret;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getExpiresSecond() {
//        return expiresSecond;
//    }
//
//    public void setExpiresSecond(int expiresSecond) {
//        this.expiresSecond = expiresSecond;
//    }
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }
//
//    public String getAppsecret() {
//        return appsecret;
//    }
//
//    public void setAppsecret(String appsecret) {
//        this.appsecret = appsecret;
//    }
//
//    public String getSessionHost() {
//        return sessionHost;
//    }
//
//    public void setSessionHost(String sessionHost) {
//        this.sessionHost = sessionHost;
//    }
//
//    public String getBnumber() {
//        return bnumber;
//    }
//
//    public void setBnumber(String bnumber) {
//        this.bnumber = bnumber;
//    }
//
//    public String getNotify_url() {
//        return notify_url;
//    }
//
//    public void setNotify_url(String notify_url) {
//        this.notify_url = notify_url;
//    }
//
//    public String getPaykey() {
//        return paykey;
//    }
//
//    public void setPaykey(String paykey) {
//        this.paykey = paykey;
//    }
//
//    public String getUnifiedorderurl() {
//        return unifiedorderurl;
//    }
//
//    public void setUnifiedorderurl(String unifiedorderurl) {
//        this.unifiedorderurl = unifiedorderurl;
//    }
}
