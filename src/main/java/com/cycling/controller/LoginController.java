package com.cycling.controller;


import com.cycling.pojo.User;
import com.cycling.service.UserService;
import com.cycling.utils.CodeUtil;
import com.cycling.utils.JWTUtils;
import com.cycling.utils.RedisUtil;
import com.cycling.utils.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description: loginController
 * @Author: qyz
 * @date: 2021/10/20 13:07
 * @Version: V1.0
 */
@RestController
@Log4j2
public class LoginController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public ResponseResult login(String phone, String password, HttpServletResponse response) {
        log.warn("phone={}", phone);
        User user = userService.findByPhone(phone);
        if ((user != null && !(user.getPassword().equals(new Md5Hash(password, user.getSalt(), 1024).toHex())))) {
            return ResponseResult.error("输入的密码错误", HttpStatus.FORBIDDEN.value());
        } else if (user == null) {
            return ResponseResult.error("该手机号未注册", HttpStatus.FORBIDDEN.value());
        }
        //当前登录时间
        long currentTimeMillis = System.currentTimeMillis();
        //生成token
        Map<String, String> map = new HashMap<>(16);
        map.put("id", String.valueOf(user.getId()));
        String token = JWTUtils.getToken(map, currentTimeMillis);
        //把该账号登陆时间以用户名作为key存入redis 有效时间为30分钟用来刷新token和踢出用户
        RedisUtil.set(String.valueOf(user.getId()), currentTimeMillis, JWTUtils.REFRESH_TOKEN_EXPIRE_TIME);
        //把token放在响应header 用于用户之后访问携带
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return ResponseResult.ok("登录成功");
    }

    @PostMapping("/login2")
    public ResponseResult login2(String phone, String password, HttpServletResponse response) {
        log.warn("phone={}", phone);
        User user = userService.findByPhone(phone);
        String code = null;
        if (RedisUtil.hasKey(phone)) {
            code = (String) RedisUtil.get(phone);
        }
        if (user != null && !(code.equals(password))) {
            return ResponseResult.error("验证码错误", HttpStatus.FORBIDDEN.value());
        } else if (user == null) {
            return ResponseResult.error("该手机号未注册", HttpStatus.FORBIDDEN.value());
        }
        //当前登录时间
        long currentTimeMillis = System.currentTimeMillis();
        //生成token
        Map<String, String> map = new HashMap<>(16);
        map.put("id", String.valueOf(user.getId()));
        String token = JWTUtils.getToken(map, currentTimeMillis);
        //把该账号登陆时间以用户名作为key存入redis 有效时间为30分钟用来刷新token和踢出用户
        RedisUtil.set(String.valueOf(user.getId()), currentTimeMillis, JWTUtils.REFRESH_TOKEN_EXPIRE_TIME);
        //把token放在响应header 用于用户之后访问携带
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return ResponseResult.ok("登录成功");
    }

    @GetMapping("/code")
    public ResponseResult getCode(String phone) {
        String code = CodeUtil.getCode(6);
        RedisUtil.set(phone, code, CodeUtil.CODE_EXPIRE_TIME);
        return ResponseResult.ok(code);
    }
}

