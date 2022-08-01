package com.hao.service;

import com.hao.pojo.StudentUser;



public interface userService {
    public StudentUser getUserByOpenId(String openid);

    public int addUser(StudentUser user);

    public int deleteUserById(int id);

    public int updateUser(StudentUser user);

    public String loginByWeixin(String code);
}
