package com.cycling.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

/**
 * @Author xpdxz
 * @ClassName SimpleDynamicOrActive
 * @Description TODO
 * @Date 2021/11/19 17:46
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDynamicOrActive {

    private Long id;

    private String title;

    private String body;

    private Long userId;
    
    private String avatar;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp time;
}
