package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UserShow
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/12/13 10:08 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserShow {
    private Long userId;

    private String avatar;

    private String level;

    private String username;

}
