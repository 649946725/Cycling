
package com.cycling.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Random;

/**
 * @ClassName: SaltUtil
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 21:06
 * @Version: V1.0
 */
public class SaltUtil {

    /*
     * 生成盐的静态方法
     */
    public static String getSalt(int n) {

        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            salt.append(aChar);
        }

        return salt.toString();
    }
}

