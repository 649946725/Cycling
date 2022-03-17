package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @ClassName: User
 * @Description: pojo
 * @Author: qyz
 * @date: 2021/10/20 13:07
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 密码
     */
    private String password;
    /**
     * 登录方式
     */
    private String method;

    /**
     * 微信openid
     */
    private String wxOpenid;
    /**
     * QQ的id
     */
    private String qqOpenid;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 账号创建时间
     */
    private Timestamp createTime;
    /**
     * 最后一次登录时间
     */
    private Timestamp lastLoginTime;

    /**
     * 盐
     */
    private String salt;
}
