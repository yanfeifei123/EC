package com.yff.ecbackend.common.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ASMUtils;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.pojo.SubscribeMessage;
import com.yff.ecbackend.common.pojo.TemplateData;
import com.yff.wechat.impl.WXPayConfigImpl;
import com.yff.wechat.wxpaysdk.WXPay;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Service
@Slf4j
public class WeChatService {

    @Autowired
    private Parameterconf parameterconf;


    /**
     * 把消息推送给微信端
     *
     * @param touser
     * @param templateId
     * @param page
     * @param map
     * @return
     */
    public Object subscribeMessage(String touser, String templateId, String page, Map<String, TemplateData> map) {

        Map<String, Object> jsonmap = new HashMap<String, Object>();
        String accessToken = this.getAccess_token();
        SubscribeMessage subscribeMessage = new SubscribeMessage();
        subscribeMessage.setAccess_token(accessToken);
        subscribeMessage.setTouser(touser);
        subscribeMessage.setTemplate_id(templateId);
        subscribeMessage.setPage(page);
        subscribeMessage.setData(map);
        String json = JSONObject.toJSONString(subscribeMessage);
        JsonNode ret = null;
        try {
            ret = Unirest.post(this.parameterconf.getSubscribeMessageurl() + accessToken).body(json).asJson().getBody();
            jsonmap.put("errcode", 0);
            jsonmap.put("errmsg", "ok");
        } catch (Exception e) {
            jsonmap.put("errcode", 1);
            jsonmap.put("errmsg", e.getMessage());
            e.printStackTrace();
        }
        System.out.println("subscribeMessage:" + JSON.toJSONString(ret));
        return jsonmap;
    }


    public String getWebAccess(String APPID, String SECRET, String CODE) {
        return String.format(parameterconf.getSessionHost(), APPID, SECRET, CODE);
    }


    /**
     * 获取模板消息Token
     *
     * @return
     */
    public String getAccess_token() {


        String access_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid=" + parameterconf.getAppid() + "&secret=" + parameterconf.getAppsecret();
        String message = "";
        try {
            URL url = new URL(access_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            //获取返回的字符
            InputStream inputStream = connection.getInputStream();
            int size = inputStream.available();
            byte[] bs = new byte[size];
            inputStream.read(bs);
            message = new String(bs, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取access_token
        JSONObject jsonObject = JSONObject.parseObject(message);
        String accessToken = jsonObject.getString("access_token");
        String expires_in = jsonObject.getString("expires_in");
//        System.out.println(JSON.toJSONString(jsonObject));
        return accessToken;
    }


    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ToolUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ToolUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


    public String httpsRequestToString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }

        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            // 创建SSLConrext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new JEE509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            // 从上述对象中的到SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

//            System.out.println(path);

            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            //设置请求方式（git|post）
            conn.setRequestMethod(method);

            //有数据提交时
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {

            }
        }
        return response;

    }

    /**
     * 用户手机解码
     *
     * @param session_key
     * @param encryptedData
     * @param iv
     * @return
     */
    public JSONObject getPhoneNumber(String session_key, String encryptedData, String iv) {
        byte[] dataByte = Base64.decode(encryptedData);
        byte[] keyByte = Base64.decode(session_key);
        byte[] ivByte = Base64.decode(iv);

        try {
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化

            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);

            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 统一下单
     *
     * @param openid
     * @param total_fee
     * @param body
     * @param request
     * @return
     */
    public Object UnifiedOrder(String tradeno, String openid, String total_fee, String body, HttpServletRequest request) {
//        this.orderid = Long.valueOf(orderid); //缓存预支付的订单id

        Map<String, String> resultMap = new HashMap<String, String>();

        WXPayConfigImpl config = null;
        WXPay wxpay = null;

        try {
            config = new WXPayConfigImpl();

            config.setAppID(parameterconf.getAppid());
            config.setMchID(parameterconf.getBnumber());
            config.setKey(parameterconf.getPaykey());

            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String nonce_str = WXPayUtil.generateNonceStr();
        String spbill_create_ip = this.getIp(request);
//        String out_trade_no = WXPayUtil.outtradeno();
//        System.out.println("out_trade_no:"+out_trade_no);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", parameterconf.getAppid());
        data.put("mch_id", parameterconf.getBnumber());
        data.put("nonce_str", nonce_str);
        data.put("body", body);
        data.put("out_trade_no", tradeno);
        data.put("total_fee", total_fee);
        data.put("spbill_create_ip", spbill_create_ip);
        String notify_url = parameterconf.getNotify_url();
//        System.out.println("notify_url:"+notify_url);
        data.put("notify_url", notify_url);
        data.put("trade_type", "JSAPI");
        data.put("openid", openid);

//        System.out.println(JSON.toJSONString(data));
        try {
            Map<String, String> rMap = wxpay.unifiedOrder(data);
            String return_code = rMap.get("return_code");
            String result_code = rMap.get("result_code");
            String nonceStr = WXPayUtil.generateNonceStr();
            resultMap.put("nonceStr", nonceStr);
            Long timeStamp = System.currentTimeMillis() / 1000;

//            System.out.println(JSON.toJSONString(result_code));

            if ("SUCCESS".equals(return_code) && return_code.equals(result_code)) {
                String prepayid = rMap.get("prepay_id");
//                System.out.println("prepayid:" + prepayid);
                resultMap.put("package", "prepay_id=" + prepayid);
                resultMap.put("signType", "MD5");
                resultMap.put("timeStamp", timeStamp + "");
                resultMap.put("appId", parameterconf.getAppid());
                String sign = WXPayUtil.generateSignature(resultMap, parameterconf.getPaykey());
                resultMap.put("paySign", sign);
                resultMap.put("prepay_id", prepayid);
//                System.out.println("生成的签名paySign : " + sign);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("统一下单:"+JSON.toJSONString(resultMap));
        return resultMap;

    }


    public String getHttps() {
        return this.parameterconf.getHttp() + "://" + this.parameterconf.getDomainname() + ":" + parameterconf.getServerPort();
    }

    /**
     * 预计订单完成时间
     *
     * @param time
     * @return
     */
    public String timeCalculation(Date time) {
        String date = DateUtil.calculationTime(time, "MINUTE", 15);
        return DateUtil.format(DateUtil.parseTime(date), "HH:mm");
    }

    /**
     * 获取订单商户号
     *
     * @return
     */
    public String createOutTradeno() {
        return WXPayUtil.outtradeno();
    }

    /**
     * 退款
     *
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    public Object refund(String out_trade_no, String total_fee) {
//        System.out.println(out_trade_no+"   "+total_fee);
        Map<String, String> data = new HashMap<String, String>();
        WXPayConfigImpl config = null;
        WXPay wxpay = null;
        try {
            config = new WXPayConfigImpl();
            config.setAppID(parameterconf.getAppid());
            config.setMchID(parameterconf.getBnumber());
            config.setKey(parameterconf.getPaykey());
            wxpay = new WXPay(config);


            data.put("out_trade_no",out_trade_no);
            data.put("out_refund_no", out_trade_no);
            data.put("transaction_id","");

            data.put("total_fee", total_fee);
            data.put("refund_fee", total_fee);
            data.put("notify_url", this.parameterconf.getRefundnotify_url());

            Map<String, String> resultsetMap = wxpay.refund(data);
            if("SUCCESS".equals(resultsetMap.get("return_code"))){
//                System.out.println(JSON.toJSONString(resultsetMap));
                log.info("退款成功！ 商户订单号：{}，退款金额：{}",resultsetMap.get("out_refund_no"),resultsetMap.get("total_fee") );
            }
            return resultsetMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String decryptReqinfo(String req_info){
        try {
            return  AESUtil.decryptData(req_info,this.parameterconf.getPaykey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
