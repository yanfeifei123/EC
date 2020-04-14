package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.aes.Aes;
import com.yff.core.util.CustomException;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Business;
import com.yff.ecbackend.business.service.BusinessService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends BaseService<User, Long> {

    @Autowired
    private UserRepository uuserRepository;

    @Autowired
    private UorderService uorderService;

    @Autowired
    private BusinessService businessService;

    public User uLogin(String userInfo, String openid) {

        User uuser = this.uuserRepository.findByOnenid(openid);
        JSONObject jsonObject = JSON.parseObject(userInfo);

        if (ToolUtil.isEmpty(uuser)) {
            uuser = new User();
            uuser.setBuildtime(new Date());
        }
        uuser.setOpenid(openid);
        uuser.setAvatarurl(jsonObject.getString("avatarUrl"));
        uuser.setGender(Integer.parseInt(jsonObject.getString("gender")));
        uuser.setNickname(jsonObject.getString("nickName"));
        return this.update(uuser);
    }


    public User getUser(String openid) {
        System.out.println(openid);
        User uuser = this.findByUserid(openid);
        String user = JSON.toJSONString(uuser);
        System.out.println(user);
        if (ToolUtil.isEmpty(uuser)) {
            uuser = new User();
            uuser.setOpenid(openid);
            uuser = this.update(uuser);
        }
        return uuser;
    }

    /**
     * 通过openid 查询用户
     *
     * @param openid
     * @return
     */
    public User findByUserid(String openid) {
        return this.uuserRepository.findByOnenid(openid);
    }

    /**
     * 商家用户验证
     *
     * @param account
     * @param password
     * @param sessionKey
     * @param iv
     * @return
     */
    public User bLogiin(String account, String password, String sessionKey, String iv) throws CustomException {
        Aes aes = new Aes();

        String a = aes.decrypt(account, sessionKey, iv);

        User user = this.uuserRepository.findByAccount(a);
        if (ToolUtil.isEmpty(user)) {
            throw new CustomException("账号不存在!");
        } else {
            String p = aes.decrypt(password, sessionKey, iv);
            String pa = this.uuserRepository.findByPassword(p, user.getOpenid());
//            System.out.println(pa);
            if (!pa.equals(user.getPassword())) {
                throw new CustomException("账号密码错误!");
            }
        }
        return user;

    }

    public float onisfirstorder(String businessid, String openid) {
        List<Uorder> uorderList = this.uorderService.findUserOrder(openid);
        float firstorder = 0;
        if (ToolUtil.isEmpty(uorderList)) {
            Business business = businessService.findOne(Long.valueOf(businessid));
            firstorder = business.getFirstorder();
        }
        return firstorder;
    }

}
