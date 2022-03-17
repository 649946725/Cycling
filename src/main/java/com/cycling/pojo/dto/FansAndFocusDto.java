package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author xpdxz
 * @ClassName FansAndFocusDto
 * @Description TODO
 * @Date 2021/10/28 18:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FansAndFocusDto {

    private Long id;

    private Long relatedUserId;

    private String avatar;

    private String username;

    private Integer sex;

    private String introduction;

    /**
     * 互相关注
     */
    private Boolean mutualConcern;

}
