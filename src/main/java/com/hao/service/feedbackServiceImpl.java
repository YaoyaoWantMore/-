package com.hao.service;

import com.hao.mapper.feedbackMapper;
import com.hao.pojo.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class feedbackServiceImpl implements feedbackService{
    @Autowired
    private feedbackMapper mapper;
    @Override
    public int addNewOne(FeedBack feedBack) {
        int i = mapper.addFeedBack(feedBack);
        return i;
    }

    @Override
    public List<FeedBack> getAll() {
        List<FeedBack> all = mapper.getAll();
        return all;
    }

    @Override
    public int delete(int id) {
        int i = mapper.deleteFeedBack(id);
        return i;
    }

    @Override
    public int complete(int id) {
        FeedBack one = mapper.getOne(id);
        one.setStatus(1);
        int i = mapper.updateStatus(one);
        return i;
    }

    @Override
    public FeedBack getOne(int id) {
        FeedBack one = mapper.getOne(id);
        return one;
    }
}
