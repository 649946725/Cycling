package com.cycling.controller;

import com.cycling.pojo.Topic;
import com.cycling.service.TopicService;
import com.cycling.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: TopicController
 * @Author: RainGoal
 * @Description: TODO
 * @Date: 2021/11/19 8:08 上午
 */
@RestController
@RequestMapping("/topic")
@Api(tags = "话题相关模块")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("findAll")
    @ApiOperation("查找全部话题")
    public ResponseResult findAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        List<Topic> all = topicService.findAll(pageNum, pageSize);
        return ResponseResult.ok(all);
    }

    @GetMapping("findByTopicName")
    @ApiOperation("通过话题名称搜索话题")
    public ResponseResult findByTopicName(String topicName, Integer pageNum, Integer pageSize) {
        List<Topic> topics = topicService.findByTopicName(topicName, pageNum, pageSize);
        return ResponseResult.ok(topics);
    }

    @PostMapping("addTopic")
    @ApiOperation("添加话题")
    public ResponseResult addTopic(Topic topic) {
        int i = topicService.addTopic(topic);
        if (i != 0) {
            return ResponseResult.ok();
        }
        return ResponseResult.error("添加出错");
    }


}
