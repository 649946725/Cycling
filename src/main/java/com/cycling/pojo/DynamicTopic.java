package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: DynamicTopic
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/15 4:35 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DynamicTopic {
    /**
     * 动态话题id
     */
    private Long id;
    /**
     * 动态id
     */
    private Long dynamicId;
    /**
     * 话题id
     */
    private Long topicId;
}
