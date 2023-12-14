package com.example.pry_iot.response;

import com.example.pry_iot.model.LogInUser;

public class UserResponse {
    private String msg;
    private String token;
    private LogInUser data;
    private String errorType;

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
    public LogInUser getData() {
        return data;
    }
    public void setData(LogInUser data) {
        this.data = data;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
