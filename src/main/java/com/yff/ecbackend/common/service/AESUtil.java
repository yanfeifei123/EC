package com.yff.ecbackend.common.service;

import com.yff.core.util.Base64Util;
import com.yff.core.util.MD5Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESUtil {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    /**
     * 生成key
     */

    static {

    }

    public static SecretKeySpec getKey(String paySign){
        SecretKeySpec key = new SecretKeySpec(MD5Util.MD5Encode(paySign, "UTF-8").toLowerCase().getBytes(), ALGORITHM);
        return key;
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data,String paySign) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        // 初始化
        SecretKeySpec key = getKey(paySign);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     *（1）对加密串A做base64解码，得到加密串B
     *（2）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String paySign) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec key = getKey(paySign);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    public static void main(String[] args) throws Exception {
        String A = "hIv8kQFj7qOYyacbPhM2vZlT8qlqdjW8iBt1uYhwWQCdoXtimbhBMAdYOSPPf4Zw4IZL2n4rlagezg/EWIVcUdC9MhDG60VrgF+nfxEbubg6KyBG4G2wzY8D8a2SUCY1kjQJWdrzJSQLg62emXMK9Rc/Tqulu/JYOP/KdVMikBwMhMQ6VrPkor+PQ9uBMIwATf3P4363eo9Lq7VGcBjZY59HBkV4ZXqRSbQez/8eJxDuMSjVlhQalq+j/DZyf8HWtZqvYQup1XRq9nmzgUueX8xtB+QnL1a5PctoW9x836PCyAIEYppIhcSMM9ZQdwiQYfu0OX05ZWBmKDBA+79e0PpcPwmnABCTuTKGHEp6IB6K29LM5M/ZUyJFGMI0y24Xpdwxpq01vXi8f8NhqPI9JRtXIUs8JU5lgzKfzGc6SpkvZyWrFAQ64fbYEMPeMWsm5UB2Q2ml1k+HhTzjTGSlDxOXL00XN0KVvajt7RyWcCwY0LrcNPUKkaAULRdsEnT8gIYUCkioEZstgHwPfYAaH5L/59yPs6EwljfXHV3I7BRGXFZb+SshIXsvKnO2MU+XSeFar4kxUY9VdQMmRQ90MZYIOdo6I0yDkCagnFsqp6JRQBiE2tyXCBfuOHXTlfLYiv+STJAnzG1xWLcm3NMCLvG7RnToZkJUWOo+AH5RluYEuFU2guaxpKJlEMoGPoocKmnwsFvz7pAMTdZ0ZX6uQwZXegvWK80FC84iM70twnX5Au4nB9c5m0OZzfIQyr40XUmLqWtfiiKhXV2Hh6MLWXDsXZ4gPe+TiLtpbjgqw3yQj/AObc5kiF85wfo4LypyvmefnwXF3zUkOv+XPPLr3z5UFwcXYcjZj6pcrvZrpzdMbboR6um42v1XuYWYLkVAsG5I/fqFzun1JsFeOom+LM9IuwAotYksFBL85crltR2LdMjZCwflrFj/RM7t5wf8rFH7MBu2L5kXFYr8oceUrAkePa9XCdJpd+/G+/+AD+gRrXerwOC4KRQl5iGfF0pGmhm3lqNjg5WVJi4uzdq/xDaPswSNQeo/0cdLYdxU8JQ=";
        String b="40288184715a5d4d01715a5d4d460000";
        System.out.println(AESUtil.decryptData(A,b));
    }

}
