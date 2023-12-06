package com.example.pry_iot.response;

import com.example.pry_iot.model.User;

public class UserResponse {
    private String msg;
    private String token;
    private User data;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public User getData() {
        return data;
    }
    public void setData(User data) {
        this.data = data;
    }
}
