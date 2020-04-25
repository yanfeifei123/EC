package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.ecbackend.business.entity.Bphoto;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.view.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BphotoService extends BaseService<Bphoto, Long> {


    @Autowired
    private WeChatService weChatService;

    @Autowired
    private Parameterconf parameterconf;


    public void setImagepath(HttpServletRequest request, Bproduct bproduct) {
        List<Bphoto> bphotos = this.findAll();
        String https = weChatService.getHttps(request);
        String imagepath = "";
        for (Bphoto bphoto : bphotos) {
            if (bphoto.getFkid() == bproduct.getId()) {
                imagepath = https + bphoto.getPath();
                bproduct.setImagepath(imagepath);
            }
        }
    }

    public void setImagepath(HttpServletRequest request, List<Bproduct> bproducts) {
        List<Bphoto> bphotos = this.findAll();
        String https = weChatService.getHttps(request);
        String imagepath = "";
        for (Bphoto bphoto : bphotos) {
            for (Bproduct bproduct : bproducts) {
                if (bphoto.getFkid() == bproduct.getId()) {
                    imagepath = https + bphoto.getPath();
                    bproduct.setImagepath(imagepath);
                }
            }
        }
    }

    public void setOrderItemImagePath(HttpServletRequest request, List<OrderItem> orderItems) {
        String https = weChatService.getHttps(request);
        String imagepath = "";
        List<Bphoto> bphotoList = this.findAll();
        for (Bphoto bphoto : bphotoList) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getProductid() == bphoto.getFkid()) {
                    imagepath = https + bphoto.getPath();
                    orderItem.setImagepath(imagepath);
                }
            }
        }
    }

    public void setUordertailImagePath(HttpServletRequest request, List<Uordertail> uordertails) {
        String https = weChatService.getHttps(request);
        String imagepath = "";
        List<Bphoto> bphotoList = this.findAll();
        for (Bphoto bphoto : bphotoList) {
            for (Uordertail uordertail : uordertails) {
                if (uordertail.getProductid() == bphoto.getFkid()) {
                    imagepath = https + bphoto.getPath();
                    uordertail.setImagepath(imagepath);
                }
            }
        }
    }
}
