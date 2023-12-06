package com.example.pry_iot.response;

import com.example.pry_iot.model.Historial_paquete;

import java.util.List;

public class HistorialResponse {
    private List<Historial_paquete> data = null;

    public List<Historial_paquete> getData() {
        return data;
    }

    public void setData(List<Historial_paquete> data) {
        this.data = data;
    }
}
