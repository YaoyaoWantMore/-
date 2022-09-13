package com.hao.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;


import java.util.Collection;

@Slf4j
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        Token token = (Token) authenticationToken;

        this.assertRealmsConfigured();
        Collection<Realm> realms = getRealms();


        for (Realm realm : realms) {
            System.out.println(realm.getName());
            if(realm.getName().contains(token.getLoginType())){
                return doSingleRealmAuthentication(realm,authenticationToken);
            }
        }

        return null;
    }


    /**
     * 重写该方法保证异常正确抛出,需要多个Realm支持不同Token，否则会出现异常覆盖
     */

}
