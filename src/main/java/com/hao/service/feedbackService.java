package com.hao.service;


import com.hao.pojo.FeedBack;

import java.util.List;

public interface feedbackService {

    public int addNewOne(FeedBack feedBack);

    public List<FeedBack> getAll();

    public int delete(int id);

    public int complete(int id);

    public FeedBack getOne(int id);

}
