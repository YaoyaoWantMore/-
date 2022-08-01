package com.hao.controller;


import com.alibaba.fastjson.JSONObject;
import com.hao.pojo.StudentUser;
import com.hao.pojo.UserInfo;
import com.hao.service.userServiceImpl;
import com.hao.util.AesCbcUtil;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v/user")
public class UserController {
    @Resource
    private userServiceImpl service;

    @RequestMapping("/userLogin")
    public UserInfo queryUser(@RequestParam("code") String code,
                              @RequestParam("encryptedData")String encryptedData,
                              @RequestParam("iv")String iv, HttpServletRequest request){
        String sendGet=service.loginByWeixin(code); //根据code去调用接口获取用户openid和session_key
        UserInfo userInfo = new UserInfo();
        StudentUser studentUser = new StudentUser();

        JSONObject json = JSONObject.parseObject(sendGet);

        System.out.println("返回过来的json数据:"+json.toString());
        String sessionkey=json.get("session_key").toString(); //会话秘钥
        String openid=json.get("openid").toString(); //用户唯一标识
        System.out.println(openid);
        StudentUser user = service.getUserByOpenId(openid);
        studentUser.setOpenid(openid);
        try{
            //拿到用户session_key和用户敏感数据进行解密，拿到用户信息。
            String decrypts= AesCbcUtil.decrypt(encryptedData,sessionkey,iv,"utf-8");//解密
            JSONObject jsons = JSONObject.parseObject(decrypts);
            String nickName=jsons.get("nickName").toString(); //用户昵称
            String jsonsds=jsons.get("avatarUrl").toString(); //用户头像
            userInfo.setNickName(nickName);
            userInfo.setAvatarUrl(jsonsds);
            studentUser.setUsername(nickName);
            if(user!=null){
                userInfo.setOpenid(openid);
            }else{
                service.addUser(studentUser);
                userInfo.setOpenid(openid);
            }
//            jsons.get("avatarUrl").toString(); //头像
//            jsons.get("gender").toString();//性别
//            jsons.get("unionid").toString(); //unionid
//            jsons.get("city").toString(); //城市
//            jsons.get("province").toString();//省份
//            jsons.get("country").toString(); //国家
        }catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;




    }
}
