package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName: Dynamic
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/20 3:58 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamic {
    /**
     * 动态id
     */
    private Long id;
    /**
     * 动态标题
     */
    private String title;
    /**
     * 动态内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Timestamp time;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 发布地址
     */
    private String position;
}
