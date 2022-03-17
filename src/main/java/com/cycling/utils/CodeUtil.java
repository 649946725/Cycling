package com.cycling.utils;

import java.util.Random;

/**
 * @ClassName: CodeUtil
 * @Author: qyz
 * @Description: TODO
 * @Date: 2021/11/26 23:11
 * @Version: 1.0
 */
public class CodeUtil {

    //验证码过期实践
    public static final long CODE_EXPIRE_TIME = 60 *  1000;
    /*
     * 生成随机验证码的静态方法
     */
    public static String getCode(int n) {

        char[] chars = "0123456789".toCharArray();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            code.append(aChar);
        }

        return code.toString();
    }
}
