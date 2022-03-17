package com.cycling.dao;

import com.cycling.pojo.Chat;
import com.cycling.pojo.User;
import com.cycling.pojo.UserInfo;
import com.cycling.pojo.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName: UserDao
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 13:13
 * @Version: V1.0
 */

@Repository
public interface UserDao {

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return User
     */
    User selectByPhone(String phone);

    /**
     * 根据id查询
     *
     * @param id
     * @return User
     */
    User selectById(Long id);

    /**
     * 根据id主键查询
     *
     * @param id id主键
     * @return User
     */
    UserInfo selectUserInfoById(Long id);

    /**
     * 查找我的界面的头像等级，性别等
     *
     * @param id
     * @return
     */
    OwnInfo getMyInfo(Long id);

    /**
     * 获取用户用于展示的信息
     *
     * @param id
     * @return com.cycling.pojo.dto.UserShow
     * @author Shubo_Yang
     * @date 2021/12/14 14:07
     */
    UserShow getUserShowInfo(Long id);

    /**
     * 获取用户获赞，关注数等
     *
     * @param id 用户id
     * @return relatedCount
     */
    RelatedCount getRelatedCount(Long id);

    /**
     * 更新用户信息
     *
     * @param id       id
     * @param userInfo userINfo
     * @return int
     */
    int updateInfo(UserInfo userInfo);

    /**
     * 获取某用户的粉丝
     *
     * @param userId
     * @param minId
     * @param num
     * @return
     */
    List<FansAndFocusDto> getFansAndSimpleUserInfo(@Param(value = "userId") Long userId, @Param("minId") Long minId, @Param("num") Integer num);

    /**
     * 获取某用户的关注
     *
     * @param userId
     * @param minId
     * @param num
     * @return
     */
    List<FansAndFocusDto> getFocusedAndSimpleUserInfo(@Param(value = "userId") Long userId, @Param("minId") Long minId, @Param("num") Integer num);

    /**
     * 获取某用户的关注的id
     *
     * @param userId userid
     * @return list
     */
    List<Integer> getFocusedUserId(Long userId);

    /**
     * 取消关注通过id
     *
     * @param id
     * @return
     */
    Integer cancelFocusedById(Long id);

    /**
     * 取消关注通过user，双id
     *
     * @param userId
     * @param focusedUserId
     * @return
     */
    Integer cancelFocusedByUser(@Param(value = "userId") Long userId, @Param(value = "focusedUserId") Long focusedUserId);

    /**
     * 关注
     *
     * @param userId
     * @param focusedUserId
     * @param time
     * @return
     */
    Integer focus(@Param("userId") Long userId, @Param("focusedUserId") Long focusedUserId, @Param("time") Timestamp time);

    /**
     * 获取个人发表的动态或活动
     *
     * @param userId
     * @param minId
     * @param num
     * @param type
     * @return
     */
    List<SimpleDynamicOrActive> getPublishOfDynamic(@Param("userId") Long userId, @Param(value = "minId") Long minId, @Param("num") Integer num);

    /**
     * 获取个人发表的活动
     *
     * @param userId
     * @param minId
     * @param num
     * @return
     */
    List<SimpleDynamicOrActive> getPublishOfActive(@Param("userId") Long userId, @Param(value = "minId") Long minId, @Param("num") Integer num);

    /**
     * 收藏的动态或活动
     *
     * @param userId
     * @param minId
     * @param num
     * @param type
     * @return
     */
    List<SimpleDynamicOrActive> getCollectOfDynamic(@Param("userId") Long userId, @Param(value = "minId") Long minId, @Param("num") Integer num);

    /**
     * 获取收藏的活动
     *
     * @param userId
     * @param minId
     * @param num
     * @return
     */
    List<SimpleDynamicOrActive> getCollectOfActive(@Param("userId") Long userId, @Param(value = "minId") Long minId, @Param("num") Integer num);

    /**
     * 用户注销
     *
     * @param userId
     * @return
     */
    Integer deleteUser(Long userId);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    Integer deleteUserInfo(Long userId);

    /**
     * 删除用户相关关系
     *
     * @return
     */
    int deleteUserRelation(Long userId);

    /**
     * 查找聊天记录
     *
     * @param receiver
     * @param sender
     * @param pageId
     * @param num
     * @return
     */
    List<Chat> findChatHistory(@Param("receiver") Long receiver, @Param("sender") Long sender, @Param("pageId") Long pageId, @Param("num") Integer num);

    /**
     * 查找头像
     *
     * @param id
     * @return
     */
    String findAvatarById(Long id);

    /**
     * 插入聊天记录
     *
     * @param chat
     * @return
     */
    Integer insertChatHistory(Chat chat);


    Integer registerUser(User user);
}
