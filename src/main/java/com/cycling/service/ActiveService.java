package com.cycling.service;

import com.cycling.pojo.Active;
import com.cycling.pojo.LimitActivity;
import com.cycling.pojo.Participation;
import com.cycling.utils.ResponseResult;

import java.util.List;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/10/28 20:20
 */
public interface ActiveService {

    /**
     * 插入
     *
     * @param active
     * @return int
     * @author Shubo_Yang
     * @date 2021/10/28 20:23
     */
    ResponseResult insert(Active active);

    /**
     * 获取全部
     *
     * @param
     * @return java.util.List<com.cycling.pojo.Active>
     * @author Shubo_Yang
     * @date 2021/10/28 20:26
     */
    ResponseResult getAllActive();

    /**
     * 分页获取
     *
     * @param id //id下标
     * @return com.github.pagehelper.PageInfo<com.cycling.pojo.Active>
     * @author Shubo_Yang
     * @date 2021/10/28 20:27
     */
    ResponseResult getAllActiveByPage(Long id);

    /**
     * @param area //地区
     * @param id
     * @return com.cycling.utils.ResponseResult
     * @author Shubo_Yang
     * @date 2021/12/7 17:18
     */
    ResponseResult getActiveByAreaAndPage(String area, Long id);

    /**
     * 根据关键字查询
     *
     * @param keywords
     * @return com.cycling.utils.ResponseResult
     * @author Shubo_Yang
     * @date 2021/12/7 18:32
     */
    ResponseResult getActiveByKeywords(String keywords);

    /**
     * 根据标签查找
     *
     * @param tags
     * @return
     */
    ResponseResult getActiveByTags(List<String> tags);

    /**
     * 获取活动详细信息
     *
     * @param id
     * @return
     */
    ResponseResult getActive(long id);

    List<LimitActivity> getLimitActivity();

    Integer insertParticipation(Participation participation);

    Integer minusRemain(Long id);

    Integer secKill(Long activityId);

    Integer findParticipation(Long activityId);
}
