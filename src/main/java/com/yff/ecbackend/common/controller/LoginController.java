package com.yff.ecbackend.common.controller;


import com.alibaba.fastjson.JSON;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.safetysupport.jwt.JwtIgnore;
import com.yff.core.safetysupport.jwt.JwtTokenUtil;
import com.yff.sysaop.SysLoga;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String index(HttpServletRequest request, Model model) {
//        System.out.println("网站首页");
        model.addAttribute("msg", "你好饭团!");
//        System.out.println("访问饭团首页!");
        return "index.html";
    }

    @PostMapping("/auth")
    @ResponseBody
    @SysLoga("系统登录")
    @JwtIgnore
    public Object auth(HttpServletRequest request, HttpServletResponse response, String code) {

        String tokenUrl = weChatService.getWebAccess(parameterconf.getAppid(), parameterconf.getAppsecret(), code);
        String appsecret = weChatService.httpsRequestToString(tokenUrl, "GET", null);
        JSONObject jsonObject = JSON.parseObject(appsecret);
        String token = "";
        if (ToolUtil.isNotEmpty(jsonObject)) {
            try {
                token = JwtTokenUtil.createJWT("", "", "", parameterconf);
                response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
                jsonObject.put("token", token);

            } catch (Exception $e) {
                System.out.println("获取Web Access Token失败:" + $e.getMessage());
            }
        }

//        System.out.println("微信登录:" + jsonObject.toJSONString());
        return jsonObject;
    }


    @PostMapping("/uLogin")
    @ResponseBody
    @SysLoga("微信授权")
    public Object uLogin(String userInfo, String openid) {

        return uuserService.uLogin(userInfo, openid);
    }


    /**
     * 商家登录初始化
     */
    @PostMapping("/bauth")
    @ResponseBody
    public Object bauth() {
        String sessionKey = ToolUtil.getRandomString(16);
        String iv = ToolUtil.getRandomString(16);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sessionKey", sessionKey);
        map.put("iv", iv);
        return map;
    }

    /**
     * 商家后台登录
     */
    @PostMapping("/bLogin")
    @ResponseBody
    public Object bLogin(HttpServletResponse response, String openid) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = null;
        try {
            boolean f =  this.uuserService.bOpenidLogiin(openid);
            if(f){
                user = this.uuserService.findByUserid(openid);
                map.put("err", 0);
                map.put("data", user);
                String token = JwtTokenUtil.createJWT(user.getId()+"", user.getName(), "", parameterconf);
                response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY,  token);
//                System.out.println("商家登陆："+user.getOpenid());
            }else{
                map.put("err", 1);
                map.put("data","你不是管理员");
            }

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
     * @param encryptedData
     * @param iv
     * @param session_key
     * @return
     */
    @RequestMapping("/binding/mobilePhone")
    @ResponseBody
    public Object mobilePhone(HttpServletRequest request, String encryptedData,  String iv,  String session_key, String openid) {
        String phone = "";
        try {
            phone = this.weChatService.getPhoneNumber(session_key, encryptedData, iv);
        }catch (Exception e){
            e.printStackTrace();
        }
        User user = this.uuserService.getUser(openid);
        if (ToolUtil.isNotEmpty(user)) {
            user.setPhone(phone);
            return this.uuserService.update(user);
        }
        return phone;
    }




}
