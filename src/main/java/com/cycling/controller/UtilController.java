package com.cycling.controller;

import com.cycling.utils.QiNiuUtil;
import com.cycling.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UtilController
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/5 9:36 上午
 */
@RestController
@RequestMapping("/utils")
@Api(tags = "工具模块")
public class UtilController {

    /**
     * 获取七牛云上传凭证
     *
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    @RequestMapping("getUploadToken")
    public ResponseResult getUploadToken() {
        Object auth = QiNiuUtil.getAuth();
        return ResponseResult.ok(auth);
    }

}
