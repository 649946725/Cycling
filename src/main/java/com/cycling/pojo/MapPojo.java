package com.cycling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/11/10 19:56
 */
@ToString
@Data
@AllArgsConstructor
public class MapPojo {
    double[] centre;//中心点数组

    List<double[]> line;//路径数组

    String time; //时长

    String longs;//长度

    List<String> tags;

    public MapPojo() {
        this.centre = new double[2];
        this.line = new LinkedList<double[]>(){
            @Override
            public String toString() {
                Iterator<double[]> it = iterator();
                if (! it.hasNext())
                    return "[]";

                StringBuilder sb = new StringBuilder();
                sb.append('[');
                for (;;) {
                    double[] e = it.next();
                    sb.append("[");
                    sb.append(e[0]);
                    sb.append(",");
                    //System.out.println(e[1]);
                    sb.append(e[1]);
                    sb.append("]");
                    //sb.append(e == this ? "(this Collection)" : e);
                    if (! it.hasNext())
                        return sb.append(']').toString();
                    sb.append(',').append(' ');
                }
            }
        };
    }


    public void setNewLine(double[] point) {
        this.line.add(point);
    }

}
