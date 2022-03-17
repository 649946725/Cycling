package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: Topic
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/29 10:43 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Topic {
    /**
     * 话题id
     */
    private Long id;

    /**
     * 话题名称
     */
    private String topicName;

}
