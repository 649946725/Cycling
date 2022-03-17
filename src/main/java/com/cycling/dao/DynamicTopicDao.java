package com.cycling.dao;

import com.cycling.pojo.DynamicTopic;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: DynamicTopicDao
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/15 4:42 下午
 */
@Repository
public interface DynamicTopicDao {
    /**
     * 添加指定动态话题
     *
     * @author RainGoal
     * @return: int
     */
    int addDynamicTopic(DynamicTopic dynamicTopic);
}
