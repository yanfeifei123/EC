package com.yff.ecbackend.users.service;


import com.alibaba.fastjson.JSON;
import com.yff.core.config.file.FileVerificationProperties;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.messagequeue.service.MessageStackService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.entity.Uorderr;
import com.yff.ecbackend.users.repository.UorderrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class UorderrService extends BaseService<Uorderr, Long> {

    @Autowired
    private FileVerificationProperties filePropertie;
    @Autowired
    private UorderService uorderService;

    @Autowired
    private MessageStackService messageStackService;



    @Autowired
    private UorderrRepository uorderrRepository;

    public Object uorderrRefund(MultipartFile multipartFile, String uorderr) {

        Uorderr uorderro = JSON.parseObject(uorderr, Uorderr.class);
        if (uorderro.isNew()) {
            uorderro.setBuildtime(new Date());
        } else {
            uorderro = this.findOne(uorderro.getId());
        }
        Uorder uorder =  this.uorderService.findOne(uorderro.getOrderid());

        uorderro.setUserid(uorder.getUserid());

        if (ToolUtil.isNotEmpty(multipartFile)) {
            try {
                this.handleUorderrFile(multipartFile, uorderro);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        boolean f= messageStackService.find(uorderro.getOrderid(),"refundOrder");
        if(!f){
            messageStackService.doOrderTask(uorder.getBranchid(),uorder.getOpenid(),uorder.getId(),"refundOrder");
        }

        return this.update(uorderro);
    }

    private void handleUorderrFile(MultipartFile multipartFile, Uorderr uorderro) throws IOException {
        File fileDir = new File(filePropertie.getUploadPath());
        String extension = ToolUtil.getFileSuffix(multipartFile.getOriginalFilename());
        String fname = ToolUtil.randomUUID();
        String filename = fileDir.getAbsolutePath() + "/" + fname + "." + extension;
        File newFile = new File(filename);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        multipartFile.transferTo(newFile);
        String url= uorderro.getUrl();
        if(ToolUtil.isNotEmpty(url)){
            url+=",/" + filePropertie.getUploadPath() + newFile.getName();
        }else{
            url="/" + filePropertie.getUploadPath() + newFile.getName();
        }
        uorderro.setUrl(url);
    }

    /**
     * 查询待退款订单
     * @param orderid
     * @return
     */
    public Object findUorderrToberefunded(Long orderid){
        return  this.uorderrRepository.findUorderrToberefunded(orderid);
    }

    /**
     * 查询已退款
     * @param orderid
     * @return
     */
    public Object findUorderrRefunded(Long orderid){
        return  this.uorderrRepository.findUorderrRefunded(orderid);
    }

    /**
     * 通过订单id查询
     * @param orderid
     * @return
     */
    public Uorderr findUorderrOrderid(Long orderid){
        return this.uorderrRepository.findUorderrOrderid(orderid);
    }


    /**
     * 统一退款信息查询
     * @param orderid
     * @return
     */
    public String findRefundInfo(Long orderid){
        Uorderr uorderr = this.findUorderrOrderid(orderid);
        String info="";
        if (ToolUtil.isNotEmpty(uorderr)) {
            if (uorderr.getEnd() == 0) {
                 info="待退款";
            } else {
                if (uorderr.getAgree() == 1) {
                    info="已退款";
                } else {
                    info="未退款";
                }
            }
        }
        return info;
    }

}
