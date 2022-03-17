package com.cycling.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author xpdxz
 * @ClassName Participation
 * @Description TODO
 * @Date 2022/3/15 16:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participation implements Serializable {

    private static final long serialVersionUID = -7517907939863421604L;
    private Long id;

    private Long limitActivityId;

    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;
}
