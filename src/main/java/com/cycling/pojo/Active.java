package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/10/28 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Active {
    private Long id;//编号

    private String title;//标题

    private Long mapId;//引用地图id

    private Long authorid;//作者id

    private Date startTime;//开始时间

    private Date stopTime;//结束时间

    private String contact;//联系方式

    private Integer ischeck;//是否审核

    private String synopsis;//简介

    private String danger;//注意
}
