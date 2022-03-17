package com.cycling.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author xpdxz
 * @ClassName LimitActivity
 * @Description TODO
 * @Date 2022/3/15 16:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitActivity {
    private Long id;

    private String depart;

    private String end;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp activityTime;

    private Integer remain;

    private Integer status;

    private Price price;

}
