package com.cycling.utils;

import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: QiNiuUtil
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/4 6:53 下午
 */
@Component
@PropertySource("classpath:application-dev.yml")
public class QiNiuUtil {

    private static String accesskey;

    private static String secretkey;

    private static String bucket;

    @Value("${qiniu.accesskey}")
    public void setAccesskey(String accesskey) {
        QiNiuUtil.accesskey = accesskey;
    }

    @Value("${qiniu.secretkey}")
    public void setSecretkey(String secretkey) {
        QiNiuUtil.secretkey = secretkey;
    }

    @Value("${qiniu.bucket}")
    public void setBucket(String bucket) {
        QiNiuUtil.bucket = bucket;
    }

    public static String getAuth() {
        Auth auth = Auth.create(accesskey, secretkey);
        String token = auth.uploadToken(bucket);
        return token;
    }

}
