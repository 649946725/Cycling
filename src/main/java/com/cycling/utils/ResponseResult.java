package com.cycling.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: R
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/9/29 12:26 下午
 */

public class ResponseResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResponseResult(String msg, int code) {
        put("msg", msg);
        put("code", code);
    }

    public static ResponseResult ok(String msg) {
        return new ResponseResult(msg, 200);
    }

    public static ResponseResult ok() {
        return new ResponseResult("success", 200);
    }


    public static ResponseResult ok(Map<String, Object> map) {
        ResponseResult ok = ok("success");
        ok.put("data", map);
        return ok;
    }

    public static <T> ResponseResult ok(T data){
        ResponseResult ok = ok("success");
        ok.put("data",data);
        return ok;
    }

    public static ResponseResult error(String msg) {
        return new ResponseResult(msg, 500);
    }

    public static ResponseResult error(String msg, int code){
        return new ResponseResult(msg,code);
    }

    @Override
    public ResponseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
