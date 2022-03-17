package com.cycling.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @ClassName: Comment
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/22 6:13 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 动态id
     */
    private Long dynamicId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Timestamp time;
}
