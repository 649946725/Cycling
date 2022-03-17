package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: DynamicShowImage
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/15 5:27 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DynamicImage {
    /**
     * 图片id
     */
    private Long id;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 动态id
     */
    private Long dynamicId;
}
