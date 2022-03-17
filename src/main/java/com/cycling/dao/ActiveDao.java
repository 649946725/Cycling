package com.cycling.dao;

import com.cycling.pojo.Active;
import com.cycling.pojo.LimitActivity;
import com.cycling.pojo.Participation;
import com.cycling.pojo.Price;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/10/28 19:06
 */
public interface ActiveDao {

    /**
     * @param active
     * @return int
     * @author Shubo_Yang
     * @date 2021/10/28 20:15
     */
    int insert(@Param(value = "active") Active active);

    /**
     * 获取所有已审核活动那
     *
     * @param
     * @return java.util.List<com.cycling.pojo.Active>
     * @author Shubo_Yang
     * @date 2021/10/28 20:20
     */
    List<Active> getAllActive();

    /**
     * 分页查询
     *
     * @param id
     * @return java.util.List<com.cycling.pojo.Active>
     * @author Shubo_Yang
     * @date 2021/11/19 16:53
     */
    List<Active> getAllAvtiveBypage(Long id);

    /**
     * 获取未经过审核活动
     *
     * @param
     * @return java.util.List<com.cycling.pojo.Active>
     * @author Shubo_Yang
     * @date 2021/11/10 19:40
     */
    List<Active> getAllActiveWithoutCheck();


    /**
     * @param tags
     * @return java.util.List<com.cycling.pojo.Active>
     * @author Shubo_Yang
     */
    List<Active> getActiveByTags(List<String> tags);

    /**
     * @param id
     * @return com.cycling.pojo.Active
     * @author Shubo_Yang
     */
    Active getActive(long id);

    List<LimitActivity> findAllLimitActivity(Long userId);

    Price findPriceById(Long id);

    Integer insertParticipation(Participation participation);

    Integer minusRemain(Long id);

    Integer findRemain(Long id);

    Integer hasParticipation(Participation participation);
}
