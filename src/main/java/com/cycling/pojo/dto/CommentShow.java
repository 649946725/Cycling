package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CommentShow
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/12/11 10:26 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentShow {
    private Long commentId;

    private Long userId;

    private String commentAvatar;

    private String commentUsername;

    private String commentLevel;

    private String commentContent;

    private String commentTime;
}
