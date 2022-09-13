package com.hao.service;

import com.hao.mapper.adminMapper;
import com.hao.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private adminMapper mapper;
    @Override
    public int changeName(String name) {
        int i = mapper.changeName(name);
        return i;
    }

    @Override
    public int changePwd(String password) {
        int i = mapper.changePwd(password);
        return i;
    }

    @Override
    public Admin queryByName(String name) {
        Admin admin = mapper.queryByName(name);

        return admin;
    }
}
