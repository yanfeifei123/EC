package com.yff.sysaop;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.uitoolkit.ToolkitStore;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.sys.entity.SysUserLog;
import com.yff.ecbackend.sys.service.SysUserLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

@Order(1)
@Aspect
@Component
public class SystemLogAop {

    static final String userkey = "openid";

    @Autowired
    private SysUserLogService sysUserLogService;

    /**
     * 建立切入点
     */
    @Pointcut("@annotation(com.yff.sysaop.SysLoga)")
    public void syslogPointCut() {}


    @AfterReturning(value = "syslogPointCut()", returning = "keys")
    public void afterReturning(JoinPoint joinPoint, Object keys) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLoga sysLoga = method.getAnnotation(SysLoga.class);
        String value = sysLoga.value();
//        System.out.println("sysLoga:" + value);
        //请求参数
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String username = getWeixinRequestOpenid(request.getParameterMap());
        if (ToolUtil.isEmpty(username)) {
            username = getWeixinReturnOpenid(keys);
        }
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setBuildtime(new Date());
        sysUserLog.setUsername(username);
        sysUserLog.setModulename(value);
        sysUserLog.setInputp(this.getInputp(request.getParameterMap()));
        sysUserLog.setOutputp(this.getOutputp(keys));
        sysUserLogService.update(sysUserLog);
    }



    private String getWeixinReturnOpenid(Object keys) {
        String openid = "";
        if (keys instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) keys;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (entry.getKey().equals(userkey)) {
                    openid = jsonObject.getString(userkey);
                    break;
                }
            }
        }
        return openid;
    }

    private String getOutputp(Object keys){
         return JSON.toJSONString(keys);
    }

    private String getInputp(Map<String, String[]> map) {
        String values = "";
        for (String key : map.keySet()) {
            for (String value : map.get(key)) {
                if (values == "") {
                    values = value;
                } else {
                    values += "," + value;
                }
            }
        }
        return values;
    }


    /**
     * 获取请求参数里的用户名称
     *
     * @param map
     * @return
     */
    private String getWeixinRequestOpenid(Map<String, String[]> map) {
        String openid = "";
        for (String key : map.keySet()) {
            if (key.equals(userkey)) {
                String[] args = map.get(userkey);
                openid = args[0];
            }
        }
        return openid;
    }


}
