package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: DynamicDetailWithComment
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/12/11 10:24 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DynamicDetailWithComment {

    private Long id;

    private String avatar;

    private String username;

    private String level;

    private String title;

    private String content;

    private List<DynamicShowImage> imgs;

    private List<CommentShow> comments;
}
