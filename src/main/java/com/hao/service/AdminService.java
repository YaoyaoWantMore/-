package com.hao.service;

import com.hao.pojo.Admin;


public interface AdminService {
    public int changeName(String name);
    public int changePwd(String password);
    public Admin queryByName(String name);
}
