package com.cycling.service;

import com.cycling.pojo.Topic;

import java.util.List;

/**
 * @ClassName: TopicService
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/18 8:46 下午
 */

public interface TopicService {
    /**
     * 查询所有话题
     *
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    List<Topic> findAll(Integer pageNum, Integer pageSize);

    /**
     * 根据名字模糊查询话题
     *
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    List<Topic> findByTopicName(String topicName, Integer pageNum, Integer pageSize);

    /**
     * 添加话题
     *
     * @param topic
     * @author RainGoal
     * @return: int
     */
    int addTopic(Topic topic);
}
