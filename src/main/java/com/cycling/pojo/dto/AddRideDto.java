package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddRideDto {
    private Integer userId;

    private String speed;

    private String duration;

    private String distance;

    private Integer mapId;
}
