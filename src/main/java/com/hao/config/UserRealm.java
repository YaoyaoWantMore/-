package com.hao.config;

import com.hao.pojo.StudentUser;
import com.hao.service.userServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


import javax.annotation.Resource;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private userServiceImpl userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        Token token = (Token) authenticationToken;


        StudentUser user = userService.getUserByOpenId(token.getUsername());

        if (user == null) {
            throw new AccountException();
        }

        ByteSource saltOfCredential = ByteSource.Util.bytes(user.getId()+"hhds");
        return new SimpleAuthenticationInfo(user, String.valueOf(token.getPassword()),
                saltOfCredential, getName());
    }
}


