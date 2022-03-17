
package com.cycling.utils;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JwtToken
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 16:24
 * @Version: V1.0
 */

public class JwtToken implements AuthenticationToken {

    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

