package com.yff.ecbackend.business.controller;


import com.yff.ecbackend.sys.service.SysUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 数据分析
 */
@Controller
@RequestMapping("/dataana")
public class DataAnaController {

    @Autowired
    private SysUserLogService sysUserLogService;

    @RequestMapping("/samedayclick")
    @ResponseBody
    public Object samedayClick() {
        Map<String, Object> map = this.sysUserLogService.samedayClick();
        return map;
    }


}
