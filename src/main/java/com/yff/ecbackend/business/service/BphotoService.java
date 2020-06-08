package com.yff.ecbackend.business.service;

import com.yff.core.config.file.FileVerificationProperties;
import com.yff.core.jparepository.service.BaseService;
import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.business.entity.Bphoto;
import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.repository.BphotoRepository;
import com.yff.ecbackend.common.service.WeChatService;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.entity.User;
import com.yff.ecbackend.users.service.UserService;
import com.yff.ecbackend.users.view.OrderItem;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BphotoService extends BaseService<Bphoto, Long> {


    @Autowired
    private FileVerificationProperties filePropertie;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private Parameterconf parameterconf;

    @Autowired
    private UserService userService;

    @Autowired
    private BphotoRepository bphotoRepository;


    public void setImagepath(Bproduct bproduct) {
        List<Bphoto> bphotos = this.findAll();
        for (Bphoto bphoto : bphotos) {
            if (bphoto.getFkid().equals(bproduct.getId())) {
                bproduct.setImagepath(bphoto.getPath());
            }
        }
    }

    public void setImagepath(List<Bproduct> bproducts) {
        List<Bphoto> bphotos = this.findAll();
        for (Bphoto bphoto : bphotos) {
            for (Bproduct bproduct : bproducts) {
                if (bphoto.getFkid().equals(bproduct.getId())) {
                    bproduct.setImagepath(bphoto.getPath());
                }
            }
        }
    }

    public void setOrderItemImagePath(List<OrderItem> orderItems) {
        List<Bphoto> bphotoList = this.findAll();
        for (Bphoto bphoto : bphotoList) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getProductid().equals(bphoto.getFkid())) {
                    orderItem.setImagepath(bphoto.getPath());
                }
            }
        }
    }




    public Object photoUpload(MultipartFile multipartFile, String openid, Long fkid, String imagepath) {
        try {
//            System.out.println("fkid:" + fkid);
            Bphoto bphoto = null;
            bphoto = this.bphotoRepository.findByfkidBphoto(fkid);
            if (ToolUtil.isEmpty(bphoto)) {
                bphoto = new Bphoto();
            } else {
//                System.out.println("imagepath:" + imagepath);
//                System.out.println("bphotopath:" + bphoto.getPath());
                if (imagepath.startsWith(bphoto.getPath())) {  //如图片为进行修改，就不上传
                    return bphoto;
                } else {
//                    String path = filePropertie.getFileRelativepath() + bphoto.getPath();
//                    ToolUtil.deleteFile(path);
                    System.out.println("文件已存在!更新文件.");
                }
            }

            File fileDir = new File(filePropertie.getUploadPath());
            String extension = ToolUtil.getFileSuffix(multipartFile.getOriginalFilename());
            String fname = ToolUtil.randomUUID();
            String filename = fileDir.getAbsolutePath() + "/" + fname + "." + extension;
            File newFile = new File(filename);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            multipartFile.transferTo(newFile);
//            System.out.println("上传路径:"+fileDir.getAbsolutePath());
            Float size = Float.parseFloat(String.valueOf(multipartFile.getSize())) / 1024;
            bphoto.setSize(size);
            bphoto.setName(fname);
            bphoto.setSuffix(extension);
            bphoto.setBuildtime(new Date());
            bphoto.setPath("/" + filePropertie.getUploadPath() + newFile.getName());
            User user = this.userService.findByUserid(openid);
            bphoto.setUserid(user.getId());
            bphoto.setFkid(fkid);
            return this.update(bphoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBphoto(Long fkid) {
        Bphoto bphoto = this.bphotoRepository.findByfkidBphoto(Long.valueOf(fkid));
        if (ToolUtil.isNotEmpty(bphoto)) {
            this.delete(bphoto);
        }
    }
}
