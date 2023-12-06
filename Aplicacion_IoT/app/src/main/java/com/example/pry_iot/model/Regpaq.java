package com.example.pry_iot.model;

import java.time.LocalDateTime;

public class Regpaq {
    private int id;
    private String nombre_paquete;
    private String lugar_paquete;
    private int user_id;
    private LocalDateTime fecha_hora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_paquete() {
        return nombre_paquete;
    }

    public void setNombre_paquete(String nombre_paquete) {
        this.nombre_paquete = nombre_paquete;
    }

    public String getLugar_paquete() {
        return lugar_paquete;
    }

    public void setLugar_paquete(String lugar_paquete) {
        this.lugar_paquete = lugar_paquete;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
