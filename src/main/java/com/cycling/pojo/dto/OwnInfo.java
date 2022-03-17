package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author xpdxz
 * @ClassName OwnInfo
 * @Description TODO
 * @Date 2021/10/28 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OwnInfo {

    private String avatar;

    private Integer level;

    private Integer sex;

    private Long exp;

    private String introduction;

    private String username;

}
