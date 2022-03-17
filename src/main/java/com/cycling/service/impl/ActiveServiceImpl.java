package com.cycling.service.impl;

import com.cycling.dao.ActiveDao;
import com.cycling.dao.UserDao;
import com.cycling.pojo.Active;
import com.cycling.pojo.LimitActivity;
import com.cycling.pojo.Participation;
import com.cycling.pojo.dto.ActiveWithMap;
import com.cycling.rabbitmq.RabbitMqConfig;
import com.cycling.service.ActiveService;
import com.cycling.utils.MapUtil;
import com.cycling.utils.RedisUtil;
import com.cycling.utils.RequestUtil;
import com.cycling.utils.ResponseResult;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.dom4j.DocumentException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shubo_Yang
 * @version 1.0
 * @date 2021/10/28 20:27
 */
@Log4j2
@Service
public class ActiveServiceImpl implements ActiveService {
    @Resource
    private ActiveDao activeDao;
    @Resource
    private UserDao userDao;

    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    private TransactionDefinition transactionDefinition;

    @Autowired
    public void setTransactionDefinition(TransactionDefinition transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String ACTIVITY_PREFIX = "activity:id:";

    private static final String USER_PREFIX = "activity:user:";

    private static final String REPEAT_REQUEST = "activity:repeat:";

    @Override
    public ResponseResult insert(Active active) {
        int i = activeDao.insert(active);
        if (i != 0) return ResponseResult.ok();
        return ResponseResult.error("服务器错误，请稍后重试");
    }

    @Override
    public ResponseResult getAllActive() {
        return ResponseResult.ok(activeDao.getAllActive());
    }

    @Override
    public ResponseResult getAllActiveByPage(Long id) {

        //PageHelper.startPage(pageNo,20);
        if (id == null) id = 5L;
        List<Active> all = activeDao.getAllAvtiveBypage(id);
        List<ActiveWithMap> allwithmao = new ArrayList<>();
        try {
            //每次往后取20个
            for (Active a : all) {
                ActiveWithMap aw = MapUtil.getMapActive(a);
                aw.setUserShow(userDao.getUserShowInfo(a.getAuthorid()));        //预留
                //log.error(userDao.getUserShowInfo(a.getAuthorid()));
                allwithmao.add(aw);
            }
        } catch (DocumentException e) {
            log.error("读取地图失败");
            log.error(e);
        }
        return ResponseResult.ok(allwithmao);
    }

    @Override
    public ResponseResult getActiveByAreaAndPage(String area, Long id) {
        return null;
    }

    @Override
    public ResponseResult getActiveByKeywords(String keywords) {
        return null;
    }

    @Override
    public ResponseResult getActiveByTags(List<String> tags) {
        return null;
    }

    @Override
    public ResponseResult getActive(long id) {
        Active a = activeDao.getActive(id);
        ActiveWithMap aw = null;
        try {
            aw = MapUtil.getMapActive(a);
        } catch (DocumentException e) {
            log.error("读取地图失败");
            log.error(e);
        }
        aw.setUserShow(userDao.getUserShowInfo(a.getAuthorid()));
        return ResponseResult.ok(aw);
    }

    @Override
    public List<LimitActivity> getLimitActivity() {
        return activeDao.findAllLimitActivity(RequestUtil.getUserId());
    }

    @Override
    public Integer insertParticipation(Participation participation) {
        return activeDao.insertParticipation(participation);
    }

    @Override
    public Integer minusRemain(Long id) {
        return activeDao.minusRemain(id);
    }

    @Override
    public Integer secKill(Long activityId) {
        Long userId = RequestUtil.getUserId();
        String activityKey = ACTIVITY_PREFIX + activityId;
        String userKey = USER_PREFIX + activityId;
        String repeat = REPEAT_REQUEST + activityId + ":" + userId;
        if (RedisUtil.incr(repeat, 1) > 1) {
            return -2;
        }
        if (RedisUtil.sHasKey(userKey, userId)) {
            //表示已经参加过了，过滤非法请求
            return -1;
        }
        if (RedisUtil.decr(activityKey, 1) < 0) {
            RedisUtil.incr(activityKey, 1);
            //库存不足
            return 0;
        }
        RedisUtil.sSet(userKey, userId);
        Participation participation = new Participation();
        participation.setTime(Timestamp.valueOf(LocalDateTime.now()));
        participation.setLimitActivityId(activityId);
        participation.setUserId(userId);
        //发送到消息队列
        rabbitTemplate.convertAndSend(RabbitMqConfig.SEC_KILL, participation);
        //秒杀成功
        return 1;
    }

    @Override
    public Integer findParticipation(Long activityId) {
        Participation participation = new Participation();
        participation.setLimitActivityId(activityId);
        participation.setUserId(RequestUtil.getUserId());
        int result = activeDao.hasParticipation(participation);
        if (result > 0) {
            return 1;
        } else if (activeDao.findRemain(activityId) < 1) {
            return -1;
        } else {
            return 0;
        }
    }


    @RabbitListener(queues = RabbitMqConfig.SEC_KILL, concurrency = "5")
    public void executeLimitActivity(@Header(AmqpHeaders.DELIVERY_TAG) long delivery, Participation participation, Channel channel) {
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Integer remain = activeDao.findRemain(participation.getLimitActivityId());
            if (remain < 1) {
                RedisUtil.set(ACTIVITY_PREFIX + participation.getLimitActivityId(), 0);
                dataSourceTransactionManager.commit(transaction);
                channel.basicAck(delivery, false);
                return;
            }
            if (activeDao.hasParticipation(participation) >= 1) {
                dataSourceTransactionManager.commit(transaction);
                channel.basicAck(delivery, false);
                return;
            }
            activeDao.insertParticipation(participation);
            activeDao.minusRemain(participation.getLimitActivityId());
            dataSourceTransactionManager.commit(transaction);
            channel.basicAck(delivery, false);
        } catch (Exception e) {
            try {
                channel.basicReject(delivery, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dataSourceTransactionManager.rollback(transaction);
            e.printStackTrace();
        }
    }
}
