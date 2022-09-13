package com.hao.config;


import org.apache.shiro.authc.UsernamePasswordToken;

public class Token extends UsernamePasswordToken {
    /**
     * 用来判断执行哪个Realm
     **/
    private String loginType;

    public Token(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }



    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}

