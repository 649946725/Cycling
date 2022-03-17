package com.cycling.dao;

import com.cycling.pojo.DynamicImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: DynamicImageDao
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/15 5:29 下午
 */
@Repository
public interface DynamicImageDao {
    /**
     * 通过动态id查询图片
     *
     * @param dynamicId
     * @author RainGoal
     * @return: java.util.List<com.cycling.pojo.DynamicShowImage>
     */
    List<DynamicImage> findByDynamicId(@Param("dynamicId") Long dynamicId);

    /**
     * 插入图片
     *
     * @author RainGoal
     * @return: java.lang.Long
     */
    Long insertImage(DynamicImage dynamicImage);
}
