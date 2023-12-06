package com.example.pry_iot.response;

import com.example.pry_iot.model.Regpaq;

public class RegpaqResponse {
    private String msg;
    private Regpaq data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Regpaq getData() {
        return data;
    }

    public void setData(Regpaq data) {
        this.data = data;
    }
}
