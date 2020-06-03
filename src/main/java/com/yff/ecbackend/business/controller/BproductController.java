package com.yff.ecbackend.business.controller;



import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.service.BproductService;
import com.yff.ecbackend.common.view.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



/**
 * 商品管理
 */
@Controller
@RequestMapping("/bproduct")
public class BproductController {

    @Autowired
    private BproductService bproductService;



    /**
     * 普通更新
     * @param bproduct
     * @return
     */
    @RequestMapping("/updatebproduct")
    @ResponseBody
    public Object updatebproduct(String bproduct){
//        System.out.println("bproduct:"+bproduct);
        CommonReturnType con = new CommonReturnType();
        if(ToolUtil.isNotEmpty(bproduct)){
            Bproduct obj = this.bproductService.updatebproduct(bproduct);
            con.setData(obj);
        }else{
            con.setCode(0);
            con.setMsg("err");
        }
        return con;
    }

    /**
     * 带文件上传
     * @param multipartFile
     * @param bproduct
     * @return
     */
    @RequestMapping(value = "/updatebproductUpLoadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object updatebproductUpLoadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("bproduct") String bproduct,@RequestParam("openid") String openid){
        CommonReturnType con = new CommonReturnType();
        Bproduct bp=  this.bproductService.updatebproductUpLoadFile(openid,bproduct,multipartFile);
        con.setData(bp);
        return con;
    }

    @RequestMapping(value = "/deleteBproduct", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBproduct(String bproductid){

        return this.bproductService.deleteBproduct(bproductid);
    }


    /**
     * 套餐选择
     * @return
     */
    @RequestMapping(value = "/choosePackage", method = RequestMethod.POST)
    @ResponseBody
    public Object choosePackage(String bproductid,String categoryid){
       return this.bproductService.choosePackage(bproductid,categoryid);
    }


    @RequestMapping(value = "/updateBindingSubbproduct", method = RequestMethod.POST)
    @ResponseBody
    public Object updateBindingSubbproduct(String bproductid, String jsonlist){
       return this.bproductService.updateBindingSubbproduct(bproductid,jsonlist);
    }


}
