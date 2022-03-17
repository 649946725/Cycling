package com.cycling.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

/**
 * @Author xpdxz
 * @ClassName UserInfo
 * @Description TODO
 * @Date 2021/10/28 11:08
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserInfo {

    /**
     * id
     */
    private Long id;

    /**
     * 外键
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 身高
     */
    private Float height;

    /**
     * 体重
     */
    private Float weight;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 经验
     */
    private Long exp;
    /**
     * 手机号
     */
    private String phone;

}
