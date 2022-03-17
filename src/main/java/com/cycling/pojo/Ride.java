package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ride {
    /**
    * 骑行记录id
    */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 骑行日期
     */
    private Timestamp finish_time;

    /**
    * 骑行时长
    */
    private String duration;

    /**
     *骑行距离
     */
    private String distance;

    /**
     * 平均速度
     */
    private String avg_speed;

    /**
     * 本次骑行地图的id
     */
    private Integer mapId;
}
