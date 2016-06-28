package com.verdant.demo.spring;

import com.verdant.demo.spring.auth.model.AuthToken;
import com.verdant.jtools.common.orm.model.TableModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/2.
 */
public class User extends TableModel implements Serializable{
    private String userId;
    private String userName;
    private String password;
    //缓存用户Session
    private AuthToken auth;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getPrimary() {
        return userId;
    }
}
