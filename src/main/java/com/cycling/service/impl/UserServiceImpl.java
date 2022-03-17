package com.cycling.service.impl;

import com.cycling.dao.UserDao;
import com.cycling.enumerate.PersonDataType;
import com.cycling.pojo.Chat;
import com.cycling.pojo.User;
import com.cycling.pojo.UserInfo;
import com.cycling.pojo.dto.FansAndFocusDto;
import com.cycling.pojo.dto.OwnInfo;
import com.cycling.pojo.dto.RelatedCount;
import com.cycling.pojo.dto.SimpleDynamicOrActive;
import com.cycling.service.UserService;
import com.cycling.utils.RequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 13:27
 * @Version: V1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User findByPhone(String phone) {

        return userDao.selectByPhone(phone);
    }

    @Override
    public User findById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public UserInfo selectUserInfoById() {
        return userDao.selectUserInfoById(RequestUtil.getUserId());
    }

    @Override
    public OwnInfo getMyInfo() {
        return userDao.getMyInfo(RequestUtil.getUserId());
    }

    @Override
    public RelatedCount getRelatedCount() {
        return userDao.getRelatedCount(RequestUtil.getUserId());
    }

    @Override
    public int updateInfo(UserInfo userInfo) {
        userInfo.setUserId(RequestUtil.getUserId());
        return userDao.updateInfo(userInfo);
    }

    @Override
    public List<FansAndFocusDto> getFansAndSimpleUserInfo(Long minId, Integer num) {
        return userDao.getFansAndSimpleUserInfo(RequestUtil.getUserId(), minId, num);
    }

    @Override
    public List<FansAndFocusDto> getFocusedAndSimpleUserInfo(Long minId, Integer num) {
        return userDao.getFocusedAndSimpleUserInfo(RequestUtil.getUserId(), minId, num);
    }

    @Override
    public List<Integer> getFocusedUserId() {
        return userDao.getFocusedUserId(RequestUtil.getUserId());
    }

    @Override
    public Integer insertChatHistory(Chat chat) {
        return userDao.insertChatHistory(chat);
    }

    @Override
    public Integer cancelFocused(Long id, Long focusedUserId) {
        if (id != null) {
            return userDao.cancelFocusedById(id);
        }
        return userDao.cancelFocusedByUser(RequestUtil.getUserId(), focusedUserId);
    }

    @Override
    public Integer focus(Long focusedUserId) {
        return userDao.focus(RequestUtil.getUserId(), focusedUserId, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<SimpleDynamicOrActive> getPublish(Long minId, Integer num, PersonDataType type) {
        List<SimpleDynamicOrActive> result;
        Long userId = RequestUtil.getUserId();
        switch (type) {
            case DYNAMIC:
                result = userDao.getPublishOfDynamic(userId, minId, num);
                break;
            case ACTIVE:
                result = userDao.getPublishOfActive(userId, minId, num);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public List<SimpleDynamicOrActive> getCollect(Long minId, Integer num, PersonDataType type) {
        List<SimpleDynamicOrActive> result;
        Long userId = RequestUtil.getUserId();
        switch (type) {
            case DYNAMIC:
                result = userDao.getCollectOfDynamic(userId, minId, num);
                break;
            case ACTIVE:
                result = userDao.getCollectOfActive(userId, minId, num);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Integer accountCancellation() {
        Long userId = RequestUtil.getUserId();
        return userDao.deleteUser(userId) + userDao.deleteUserInfo(userId) + userDao.deleteUserRelation(userId);
    }

    @Override
    public String getAvatar(Long id) {
        return userDao.findAvatarById(id);
    }

    @Override
    public List<Chat> getChatHistory(Long pageId, Integer num, Long receiver) {
        return userDao.findChatHistory(receiver, RequestUtil.getUserId(), pageId, num);
    }


}
