package com.example.pry_iot.response;

import com.example.pry_iot.model.Paquete;

import java.util.List;

public class PaqueteResponse {
    private List<Paquete> data = null;

    public List<Paquete> getData() {
        return data;
    }

    public void setData(List<Paquete> data) {
        this.data = data;
    }
}
