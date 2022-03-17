package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xpdxz
 * @ClassName Price
 * @Description TODO
 * @Date 2022/3/15 16:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private Long id;

    private String images;

    private String introduction;

    private Long remain;

    private Double money;

    private String title;
}
