package com.cycling.service;

import com.cycling.pojo.dto.AddDynamicPojo;
import com.cycling.pojo.dto.DynamicDetailWithComment;
import com.cycling.pojo.dto.DynamicShow;
import com.cycling.utils.ResponseResult;

import java.util.List;

/**
 * @InterfaceName: DynamicService
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/29 4:43 下午
 */

public interface DynamicService {


    ResponseResult addDynamic(AddDynamicPojo addDynamicPojo);

    /**
     * 查找用户动态
     *
     * @param id
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult findDynamicByUser(String id);

    /**
     * 根据地区获取动态
     *
     * @param area
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult findDynamicByArea(String area);

    /**
     * 获取推荐动态
     *
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult findDynamicRecommend();

    /**
     * 通过关注获取动态
     *
     * @author RainGoal
     * @return: java.util.List<com.cycling.pojo.dto.DynamicShow>
     */
    List<DynamicShow> findDynamicByAttention();

    /**
     * 根据id获取动态详情
     *
     * @param id
     * @author RainGoal
     * @return: com.cycling.pojo.dto.DynamicDetailWithComment
     */
    DynamicDetailWithComment findDynamicById(Long id);

    List<DynamicShow> findDynamicByContent(String content);
}
