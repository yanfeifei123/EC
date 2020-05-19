package com.yff.ecbackend.common.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.core.safetysupport.jwt.JwtTokenUtil;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {


    @Autowired
    private WeChatService weChatService;

    @Autowired
    private Parameterconf parameterconf;

    @Autowired
    private UserService uuserService;


    @RequestMapping(value = "/")
    @JwtIgnore
    public String index(HttpServletRequest request, Model model) {
//        System.out.println("网站首页");
        model.addAttribute("msg", "你好饭团!");
        System.out.println("访问饭团首页!");
        return "index.html";
    }

    @PostMapping("/auth")
    @ResponseBody
    @JwtIgnore
    public Object auth(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {

        String tokenUrl = weChatService.getWebAccess(parameterconf.getAppid(), parameterconf.getAppsecret(), params.get("code"));
        String appsecret = weChatService.httpsRequestToString(tokenUrl, "GET", null);
        String token = "";
        JSONObject jsonObject = JSON.parseObject(appsecret);

        if (ToolUtil.isNotEmpty(jsonObject)) {
            try {
                token = JwtTokenUtil.createJWT("", "", "", parameterconf);
                response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
                jsonObject.put("token", token);
//                System.out.println(JSON.toJSONString(jsonObject));
            } catch (Exception $e) {
                System.out.println("获取Web Access Token失败:" + $e.getMessage());
            }
        }
        System.out.println("获取token:" + token);
        return jsonObject;
    }


    @PostMapping("/uLogin")
    @ResponseBody
    public Object uLogin(@RequestBody Map<String, String> params ) {

        return uuserService.uLogin(params.get("userInfo") ,params.get("openid")  );
    }


    /**
     * 商家登录初始化
     */
    @PostMapping("/bauth")
    @ResponseBody
    public Object bauth() {
        String sessionKey = ToolUtil.getRandomString(16);
        String iv = ToolUtil.getRandomString(16);
        Map<String, String> map = new HashMap<>();
        map.put("sessionKey", sessionKey);
        map.put("iv", iv);
        return map;
    }

    /**
     * 商家后台登录
     */
    @PostMapping("/bLogin")
    @ResponseBody
    public Object bLogin(@RequestBody Map<String, String> params , HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        User user = null;
        try {
            user = this.uuserService.bLogiin(params.get("account") ,params.get("password") ,params.get("sessionKey")  ,params.get("iv"));
            map.put("err", 0);
            map.put("data", user);
            String token = JwtTokenUtil.createJWT(user.getId() + "", user.getName(), "", parameterconf);
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
//            request.getSession().setAttribute("openid",user.getOpenid());
//            System.out.println("商家登陆："+user.getOpenid());
        } catch (Exception e) {
            map.put("err", 1);
            map.put("data", e.getMessage());
        }
        return map;
    }


    /**
     * 获取用户手机号
     *
     * @param request
     * @return
     */
    @RequestMapping("/binding/mobilePhone")
    @ResponseBody
    public Object login(@RequestBody Map<String, String> params , HttpServletRequest request) {
        JSONObject obj = this.weChatService.getPhoneNumber(params.get("session_key") ,params.get("encryptedData") , params.get("iv") );
        String phone = obj.get("phoneNumber").toString();
        User user = this.uuserService.getUser(params.get("openid") );
        if (ToolUtil.isNotEmpty(user)) {
            user.setPhone(phone);
            return this.uuserService.update(user);
        }
        return null;
    }


}
