package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: AddDynamicPojo
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/26 3:39 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDynamicPojo {

    private String title;

    private String content;

    private String position;

    private Long[] topicId;

    private String[] imgName;
}
