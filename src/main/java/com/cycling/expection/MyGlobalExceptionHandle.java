package com.cycling.expection;

import com.cycling.utils.ResponseResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


/**
 * @ClassName: MyGlobalExceptionHandle
 * @Description: 在该类中，可以定义多个方法，不同的方法处理不同的异常
 * @Author: qyz
 * @date: 2021/10/28 15:23
 * @Version: V1.0
 */

@RestControllerAdvice
public class MyGlobalExceptionHandle {


    /**
     * 捕获全部shiro异常
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public ResponseResult handleShiroException(ShiroException e) {

        return ResponseResult.error("无权访问:" + e.getMessage(), 401);

    }

    /**
     * 捕捉没有该有的权限抛出的shiro异常  没有授权的异常
     *
     * @param e e
     * @return response
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseResult handleUnauthorizedException(UnauthorizedException e) {
        return ResponseResult.error("无权访问:当前用户没有此请求所需权限(" + e.getMessage() + ")", 401);
    }


    /**
     * 捕捉以游客访问时无权访问的异常 没有认证登录异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseResult handle401(UnauthenticatedException e) {
        return ResponseResult.error("无权访问:当前用户没有登录，请先登录" + e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }


    /**
     * 捕捉404异常  没有发现页面的异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseResult handle(NoHandlerFoundException e) {
        return ResponseResult.error(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseResult sqlHandler(SQLException sqlException, HttpServletRequest request) {
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            return ResponseResult.error("注销失败", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseResult.error("操作失败", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult globalException(HttpServletRequest request, Throwable ex) {
        return ResponseResult.error(ex.toString() + ": " + ex.getMessage());
    }
}
