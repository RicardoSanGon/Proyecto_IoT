package com.example.pry_iot.response;

import com.example.pry_iot.model.Paquete;

public class UpdatePackResponse {

    private String msg;
    private Paquete data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Paquete getData() {
        return data;
    }

    public void setData(Paquete data) {
        this.data = data;
    }
}
