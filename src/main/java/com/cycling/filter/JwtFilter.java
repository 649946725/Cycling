package com.cycling.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cycling.utils.JWTUtils;
import com.cycling.utils.JwtToken;
import com.cycling.utils.RedisUtil;
import com.cycling.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JwtFilter
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 13:51
 * @Version: V1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {


    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期也就是到30分钟没有，未过期就返回新的jwt——Token且继续正常访问
     *
     * @param request
     * @param response
     * @return
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        String jwt_token = ((HttpServletRequest) request).getHeader("token");
        //从jwt——token中拿出用户名和登录时间来判断Redis是否存在所对应的RefreshToken
        String id = JWTUtils.getTokenInfo(jwt_token,"id").asString();
        Long currentTime = JWTUtils.getTokenInfo(jwt_token,"currentTime").asLong();
        // 判断Redis中RefreshToken是否存在
        if (RedisUtil.hasKey(id)) {
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            Long currentTimeMillisRedis = (Long) RedisUtil.get(id);
            // 获取当前jwt——Token中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，对jwt——Token进行刷新
            if (currentTimeMillisRedis.equals(currentTime)) {
                // 获取当前最新时间戳
                Long currentTimeMillis = System.currentTimeMillis();
                //进行刷新redis中的RefreshToken
                RedisUtil.set(id, currentTimeMillis,
                        JWTUtils.REFRESH_TOKEN_EXPIRE_TIME);
                // 刷新jwt——Token，设置时间戳为当前最新时间戳也就是重新生成jwt——token
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                jwt_token = JWTUtils.getToken(map, currentTimeMillis);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Authorization", jwt_token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                httpServletResponse.setHeader("TOKEN_EXPIRE_CODE", String.valueOf(JWTUtils.TOKEN_EXPIRE_CODE));
                return true;
            }
        }
        return false;
    }

    /**
     * 把服务端的响应转换为客户端的响应设置一些基本状态字符类型，然后返回指定的信息
     *
     * @param response
     * @param msg
     */
    private void responseError(ServletResponse response, String msg,int code) {

        //把服务端的response转换为客户端的response
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //给客户端响应的状态设置为401
        httpServletResponse.setStatus(HttpStatus.OK.value());
        //设置字符集为utf-8
        httpServletResponse.setCharacterEncoding("UTF-8");
        //设置响应内容的类型
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //把响应的信息放在客户端响应体中
        try {
            //将msg封装为一个Result对象并用ObjectMapper().writeValueAsString方法将其json话并返回
            String message = new ObjectMapper().writeValueAsString(new ResponseResult(msg, code));
            //向客户端响应体中追加信息
            httpServletResponse.getWriter().append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建shiro token  覆盖AuthenticatingFilter类中createToken方法
     * 因为executeLogin方法重需要创建token但不符合我们的需要所以我们去重写它
     * 不符合要求是因为
     * 我们需要jwt生成的token
     *
     * @param request
     * @param response
     * @return
     */

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        //重请客户端响应体请求头中获取token
        String jwtToken = ((HttpServletRequest) request).getHeader("token");

        if (jwtToken != null) {
            return new JwtToken(jwtToken);
        }

        return null;
    }

    /**
     * 判断是否登录 在进行登录的时候才会走这个方法
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            responseError(response, "认证失败",HttpStatus.UNAUTHORIZED.value());
            //抛出异常说明没有登录 返回false
            return false;
        }
    }

    /**
     * shiro验证成功调用  isAccessAllowed返回true时调用该方法
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        //由于我们重写了createToken方法 所以重shiro中的token中获取的凭证就是jwt的token
        String jwt_token = (String) token.getPrincipal();
        if (jwt_token != null) {
            try {
                if (JWTUtils.verify(jwt_token)) {
                    //从jwt——token中拿出用户名和登录时间来判断Redis是否存在所对应的RefreshToken
                    String id = JWTUtils.getTokenInfo(jwt_token,"id").asString();
                    Long currentTime = JWTUtils.getTokenInfo(jwt_token,"currentTime").asLong();
                    //判断Redis是否存在所对应的RefreshToken
                    if (RedisUtil.hasKey(id)) {
                        //如果存在 则取出RefreshToken中的登陆时间
                        Long currentTimeMillisRedis = (Long) RedisUtil.get(id);
                        //判断jwt——token的登录时间是否与redis存的RefreshToken中的登录时间一致
                        //如果一致返回true 放行不一致说明账号被顶了 返回false 登录失败 拦截
                        if (currentTimeMillisRedis.equals(currentTime)) {
                            final HttpServletRequest servletRequest = (HttpServletRequest) request;
                            servletRequest.setAttribute("id", id);
                            return true;
                        }
                    }
                }
                //如果一致返回false不一致说明账号被顶了 返回false 拦截
                return false;
            } catch (Exception e) {
                //如果token验证失败 并获取验证失败的原因
                Throwable throwable = e.getCause();
                //如果是因为token过期了验证失败需要续签
                if (e instanceof TokenExpiredException) {
                    //续签成功返回true 放行  失败 拦截
                    if (refreshToken(request, response)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * isAccessAllowed为false时调用，验证失败
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        //把服务端request转换客户端request把服务端response转换客户端response
        this.sendChallenge(request, response);
        responseError(response, "token 验证失败",HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    /**
     * 是否进行登录请求
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        //从客户端发起的请求头中取token
        String token = ((HttpServletRequest) request).getHeader("token");
        //如果token不为空说明用户是登录状态发起的请求 如果为空则不是登录状态发起的请求
        if (token != null) {
            return true;
        }
        return false;
    }


    /**
     * 拦截器的前置方法，进行跨域处理  也就是在访问请求时先进行跨域处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //进行跨域处理
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        //如果不携带token，返回false 直接拦截 不去验证shiro
        if (!isLoginAttempt(request, response)) {
            responseError(httpServletResponse, "没有携带token",HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return super.preHandle(request, response);

    }

}

