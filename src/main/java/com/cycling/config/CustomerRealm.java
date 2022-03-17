package com.cycling.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cycling.pojo.User;
import com.cycling.service.UserService;
import com.cycling.utils.JWTUtils;
import com.cycling.utils.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: CustomReam
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 18:23
 * @Version: V1.0
 */
@Component
public class CustomerRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    //让能识别自定义token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("用户授权");
        String token = (principalCollection.toString());
        String id =  JWTUtils.getTokenInfo(token,"id").asString();
        User user = userService.findById(Long.valueOf(id));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //测试
        if (user.getPhone().equals("13994722068")) {
            Set<String> role = new HashSet<>();
            role.add("admin");
            info.setRoles(role);
        } else {
            Set<String> role = new HashSet<>();
            role.add("user");
            info.setRoles(role);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("shiro身份认证");
        String token = (String) authenticationToken.getCredentials();
        String id = JWTUtils.getTokenInfo(token,"id").asString();
        User user = userService.findById(Long.valueOf(id));
        if (ObjectUtils.isEmpty(user)) {
            throw new AuthenticationException("认证失败！");
        } else {
            return new SimpleAuthenticationInfo(token, token, "MyRealm");
        }


    }
}
