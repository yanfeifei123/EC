package com.yff.ecbackend.users.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yff.core.jparepository.page.Paging;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.aes.Aes;
import com.yff.core.util.CustomException;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.service.BbranchService;
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
    private UserRepository userRepository;

    @Autowired
    private UorderService uorderService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BbranchService bbranchService;

    public User uLogin(String userInfo, String openid) {
//        System.out.println("uLogin:" + userInfo + "  openid:" + openid);
        User user = this.userRepository.findByOnenid(openid);
        JSONObject jsonObject = JSON.parseObject(userInfo);
        if (ToolUtil.isEmpty(user)) {
            user = new User();
            user.setBuildtime(new Date());
            user.setOpenid(openid);
            user.setAvatarurl(jsonObject.getString("avatarUrl"));
            user.setGender(Integer.parseInt(jsonObject.getString("gender")));
            user.setNickname(jsonObject.getString("nickName"));
            return this.update(user);
        }
        return user;
    }


    public User getUser(String openid) {
        User uuser = this.findByUserid(openid);

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
        return this.userRepository.findByOnenid(openid);
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

        User user = this.userRepository.findByAccount(a);
        if (ToolUtil.isEmpty(user)) {
            throw new CustomException("账号不存在!");
        } else {
            String p = aes.decrypt(password, sessionKey, iv);
            String pa = this.userRepository.findByPassword(p, user.getOpenid());
//            System.out.println(pa);
            if (!pa.equals(user.getPassword())) {
                throw new CustomException("账号密码错误!");
            }
        }
        return user;

    }

    /**
     * 通过分店id查询管理者
     *
     * @param branchid
     * @return
     */
    public List<User> findByBranchid(Long branchid) {
        return this.userRepository.findByBranchid(branchid);
    }


    public boolean bOpenidLogiin(String openid) {
        User user = this.userRepository.findByOnenid(openid);
        Bbranch bbranch = this.bbranchService.findOne(user.getBranchid());
        return ToolUtil.isNotEmpty(bbranch);
    }

    public List<User> findBybranchUser(Long branchid, String name) {
        return this.userRepository.findBybranchUser(branchid, name);
    }

    public List<User> findByUsers(String name, String pageNum, String pageSize) {
//        System.out.println(pageNum+"   "+pageSize);
        List<User> list = this.userRepository.findByUsers(name, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//        System.out.println(JSON.toJSONString(list));
        return list;
    }

    public int usertotalPage(String name, String pageSize) {
//        System.out.println(pageSize);
        int totalRecord = this.userRepository.usertotalPage(name);
        Paging paging = new Paging();
        paging.setPagesize(Integer.parseInt(pageSize));
        paging.setTotalRecord(totalRecord);
        return paging.getTotalPage();
    }

    public List<User> unboundUser(String userid, String name) {
        User user = this.findOne(Long.valueOf(userid));
        Long branchid = user.getBranchid();
        user.setBranchid(null);
        this.update(user);
        return this.findBybranchUser(branchid, name);
    }

    public User onboundUser(String userid,String branchid){
        User user = this.findOne(Long.valueOf(userid));
        user.setBranchid(Long.valueOf(branchid));
        return this.update(user);
    }


}
