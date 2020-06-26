package com.yff.ecbackend.users.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.yff.ecbackend.users.entity.User;
import lombok.Data;
import java.util.Date;


/**
 * 用户会员
 */
@Data
public class Usermember {

    private Long id;
    private String nickname;
    private String avatarurl;
    private float money;
    private Long branchid;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

}
