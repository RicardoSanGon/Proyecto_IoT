package com.example.pry_iot.response;

import com.example.pry_iot.model.Actpaq;

public class ActpaqResponse {
    private String msg;
    private Actpaq data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Actpaq getData() {
        return data;
    }

    public void setData(Actpaq data) {
        this.data = data;
    }
}
