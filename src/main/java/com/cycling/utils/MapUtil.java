package com.cycling.utils;

import com.cycling.pojo.Active;
import com.cycling.pojo.MapPojo;
import com.cycling.pojo.dto.ActiveWithMap;
import lombok.extern.log4j.Log4j2;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 处理Map数据
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/11/10 19:46
 */
@Log4j2
public class MapUtil {
    public static MapPojo getMap(long id) throws DocumentException {
        if(RedisUtil.hasKey("Map"+id)){
            log.error(RedisUtil.get("Map"+id));
            System.out.println(RedisUtil.get("Map"+id));
            return  (MapPojo) RedisUtil.get("Map"+id);
        }else{
            MapPojo mappojo = new MapPojo();

            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("src\\main\\resources\\MapFile\\Map"+id+".xml"));
            Element root = document.getRootElement();
            double[] point = new double[2];
            int i =0;
            for (Iterator it = root.element("Centre").elementIterator(); it.hasNext(); i++){
                Element element = (Element) it.next();
                Attribute attribute = element.attribute("value");
                point[i] = Double.parseDouble(attribute.getText()) ;
            }
            mappojo.setCentre(point);
            for (Iterator it = root.element("Line").elementIterator();it.hasNext();){
                double[] pointline = new double[2];
                i = 0;
                Element element =(Element) it.next();
                for (Iterator its = element.elementIterator();its.hasNext();i++){
                    Attribute attr = ((Element)its.next()).attribute("value");
                    pointline[i]= Double.parseDouble(attr.getText());
                }
                mappojo.setNewLine(pointline);
            }
            mappojo.setTime(root.element("Info").element("time").getText());
            mappojo.setLongs(root.element("Info").element("long").getText());
            List<String> tags = new ArrayList<>();
            for (Iterator it = root.element("Tags").elementIterator();it.hasNext();){
                Element element =(Element) it.next();
                tags.add(element.getText());
            }
            mappojo.setTags(tags);
            RedisUtil.set("Map"+id,mappojo);
            return mappojo;
        }
        //如果redis不存在这个map 则i/o读取xml文件生成dto


    }
    public static ActiveWithMap getMapActive(Active active) throws DocumentException {
        return new ActiveWithMap(null,active,getMap(active.getMapId()));
    }

}
