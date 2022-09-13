package com.hao.mapper;

import com.hao.pojo.riderUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface riderUserMapper {
    //查询全部
    public List<riderUser> queryall();
    //查询一个骑手
    public riderUser queryone(String openid);
    //注册
    public int enroll(riderUser user);

    //钱包更新
    public int changeMoney(@Param("money") double money, @Param("openid") String openid);

    //订单更新
    public int changeOrder(@Param("orderNum")String orderNum,@Param("openid")String openid);

    //注册通过
    public int setStatusTure(String openid);

    //封号
    public  int setStatusFalse(String openid);

}
