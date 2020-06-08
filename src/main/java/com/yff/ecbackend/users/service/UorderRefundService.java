package com.yff.ecbackend.users.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.repository.UorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.spi.LocaleNameProvider;


/**
 * 订单退款流程
 */
@Service
public class UorderRefundService extends BaseService<Uorder, Long> {

    @Autowired
    private UorderRepository uorderRepository;

    @Autowired
    private MessageStackService messageStackService;


    public Object inspectRefund(String orderid) {
      return  messageStackService.find(Long.valueOf(orderid));
    }


}


