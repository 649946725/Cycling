package com.cycling.dao;

import com.cycling.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: CommentDao
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/29 9:39 上午
 */
@Repository
public interface CommentDao {

    /**
     * 根据动态id查询所有的评论
     *
     * @param id
     * @author RainGoal
     * @return: java.util.List<com.cycling.pojo.Comment>
     */
    List<Comment> findCommentByDynamic(@Param("id") Integer id);

    /**
     * 添加评论
     *
     * @param comment
     * @author RainGoal
     * @return: int
     */
    int addComment(Comment comment);

    /**
     * 删除评论
     *
     * @param id
     * @author RainGoal
     * @return: int
     */
    int deleteComment(@Param("id") Integer id);

}
