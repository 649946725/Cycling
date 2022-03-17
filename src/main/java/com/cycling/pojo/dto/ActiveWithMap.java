package com.cycling.pojo.dto;

import com.cycling.pojo.Active;
import com.cycling.pojo.MapPojo;
import com.cycling.utils.MapUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.dom4j.DocumentException;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/11/10 19:49
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class ActiveWithMap {
    UserShow userShow;
    Active active;
    MapPojo map;

    public ActiveWithMap(Active active) throws DocumentException {
        this.active = active;
        map = MapUtil.getMap(active.getMapId());
    }

}
