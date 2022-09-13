package com.hao.controller;

import com.hao.pojo.FeedBack;
import com.hao.service.feedbackServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v/my/feedback")
public class FeedBackController {
    @Resource
    private feedbackServiceImpl service;

    @RequestMapping(value = "/addNew",produces = "application/json;charset=UTF-8")
    public String addNew(@RequestBody Map<String,Object> map){

        FeedBack feedBack = new FeedBack();
        feedBack.setStatus(0);
        System.out.println("map的值为"+map.get("question_type")+"类型为"+map.get("question_type").getClass().getName());
        Integer question_type = (Integer) map.get("question_type");
        feedBack.setQuestion_type(question_type.intValue());
        if(feedBack.getQuestion_type()==0){
            feedBack.setOrder_id((String)map.get("order_id"));
        }
        feedBack.setContact_type((String)map.get("contact_type"));
        feedBack.setContact((String)map.get("contact"));
        feedBack.setDescription((String)map.get("description"));
        feedBack.setId(-1);
        feedBack.setOpenid((String)map.get("openid"));
        int i = service.addNewOne(feedBack);
        if(i>0){
            return "1";
        }else {
            return "0";
        }

    }



}
