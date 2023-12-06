package com.example.pry_iot.response;

import com.example.pry_iot.model.Reguser;

public class ReguserResponse {
    private String msg;
    private Reguser data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Reguser getData() {
        return data;
    }

    public void setData(Reguser data) {
        this.data = data;
    }
}
