package com.hao.service;

import com.hao.pojo.riderUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface riderService {
    //查询全部
    public List<riderUser> queryall();
    //查询一个骑手
    public riderUser queryone(String openid);
    //注册
    public int enroll(riderUser user);

    //钱包更新
    public int changeMoney(double money,String openid);

    //注册通过
    public int setStatusTure(String openid);

    //封号
    public  int setStatusFalse(String openid);

}
