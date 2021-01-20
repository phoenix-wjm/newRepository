package com.sfac.geniusdirecruit.common.utile;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * MD5 Util，密码加密
 * @author: huangyiheyi
 * @date: 2021年01月11日
 */
public class MD5Util {
    private static final String SALT = "&%5123***&&%%$$#@";

    public static String getMD5(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String base = str + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("111111"));//51c017d79ab2eea8548f22543409cd05
    }
}
