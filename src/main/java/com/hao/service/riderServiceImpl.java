package com.hao.service;

import com.hao.mapper.riderUserMapper;
import com.hao.pojo.riderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class riderServiceImpl implements riderService{
    @Autowired
    riderUserMapper mapper;
    @Override
    public List<riderUser> queryall() {
        List<riderUser> queryall = mapper.queryall();
        return queryall;
    }

    @Override
    public riderUser queryone(String openid) {
        riderUser queryone = mapper.queryone(openid);
        return queryone;
    }

    @Override
    public int enroll(riderUser user) {
        int enroll = mapper.enroll(user);
        return enroll;
    }

    @Override
    public int changeMoney(double money, String openid) {
        int i = mapper.changeMoney(money, openid);
        return i;
    }

    @Override
    public int setStatusTure(String openid) {
        int i = mapper.setStatusTure(openid);
        return i;
    }

    @Override
    public int setStatusFalse(String openid) {
        int i = mapper.setStatusFalse(openid);
        return i;
    }
}
