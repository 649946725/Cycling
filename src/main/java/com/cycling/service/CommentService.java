package com.cycling.service;

import com.cycling.pojo.Comment;
import com.cycling.utils.ResponseResult;

/**
 * @InterfaceName: CommentService
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/10/29 7:19 下午
 */

public interface CommentService {
    /**
     * 根据动态查询评论
     *
     * @param id
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult findCommentByDynamic(Integer id);

    /**
     * 添加评论
     *
     * @param comment
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult addComment(Comment comment);

    /**
     * 删除评论
     *
     * @param id
     * @author RainGoal
     * @return: com.cycling.utils.ResponseResult
     */
    ResponseResult deleteComment(Integer id);
}
