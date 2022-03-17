package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: DynamicShow
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/12/3 9:08 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DynamicShow {
    private Long id;

    private String avatar;

    private String username;

    private String level;

    private String title;

    private String content;

    private List<DynamicShowImage> imgs;
}
