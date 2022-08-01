package com.hao.service;

import com.hao.mapper.userMapper;
import com.hao.pojo.StudentUser;
import com.hao.util.Httprequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService{
    @Autowired
    private userMapper userMapper;

    public void setUserMapper(com.hao.mapper.userMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public StudentUser getUserByOpenId(String openid) {
        return userMapper.getUserByOpenId(openid);
    }

    @Override
    public int addUser(StudentUser user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int updateUser(StudentUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public String loginByWeixin(String code) {
        //发送    https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code 获取用户的openid和session_key
        //注意这个是 WeChatTool.wxspAppid 是微信小程序的appid 从微信小程序后台获取 WeChatTool.wxspSecret 这个也一样，我这里是用了常量来进行保存方便多次使用
        String url="https://api.weixin.qq.com/sns/jscode2session";
        String params = "appid=wxe35781703e6544b7" + "&secret=" + "8e0c1ee4a65b34b16905083f4b040442" + "&js_code=" + code + "&grant_type=authorization_code";
        String sendGet = Httprequests.sendGet(url, params); //发起请求拿到key和openid
        return sendGet;

    }
}
