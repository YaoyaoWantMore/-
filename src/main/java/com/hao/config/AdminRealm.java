package com.hao.config;

import com.hao.pojo.Admin;
import com.hao.service.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Resource
    private AdminServiceImpl service;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入了管理");
        Token token = (Token) authenticationToken;

        Admin user = service.queryByName(token.getUsername());
        if(user==null){
            throw new AccountException();
        }


        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
