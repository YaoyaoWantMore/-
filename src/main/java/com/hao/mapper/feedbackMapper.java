package com.hao.mapper;

import com.hao.pojo.FeedBack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface feedbackMapper {

    public int addFeedBack(FeedBack feedBack);

    public int deleteFeedBack(int id);

    public List<FeedBack> getAll();

    public FeedBack getOne(int id);

    public int updateStatus(FeedBack feedBack);

}
