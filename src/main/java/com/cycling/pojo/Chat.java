package com.cycling.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author xpdxz
 * @ClassName Chat
 * @Description TODO
 * @Date 2022/3/10 20:43
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Chat implements Serializable {


    private static final long serialVersionUID = -1883918569519665310L;
    private Long id;

    private String content;

    private Integer type;

    private Long receiver;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;

    private Long sender;

    private String receiverPic;

    private String senderPic;
}
