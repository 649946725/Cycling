package com.cycling.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author xpdxz
 * @ClassName RelatedCount
 * @Description TODO
 * @Date 2021/10/28 13:57
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RelatedCount {

    private Long fansCount;

    private Long focusCount;

    private Long praisedCount;

    private Long visitorCount;

}
