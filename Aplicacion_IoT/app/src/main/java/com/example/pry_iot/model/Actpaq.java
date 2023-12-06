package com.example.pry_iot.model;

import java.time.LocalDateTime;

public class Actpaq {
    private int id;
    private String nombre_paquete;
    private String lugar_paquete;
    private double datos_sensor_ph;
    private double datos_sensor_turbidez;
    private double datos_sensor_temperatura;
    private double datos_sensor_conductividad;
    private double datos_sensor_nivel_agua;
    private int user_id;
    private LocalDateTime fecha_hora;
    private boolean status;

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

    public double getDatos_sensor_ph() {
        return datos_sensor_ph;
    }

    public void setDatos_sensor_ph(double datos_sensor_ph) {
        this.datos_sensor_ph = datos_sensor_ph;
    }

    public double getDatos_sensor_turbidez() {
        return datos_sensor_turbidez;
    }

    public void setDatos_sensor_turbidez(double datos_sensor_turbidez) {
        this.datos_sensor_turbidez = datos_sensor_turbidez;
    }

    public double getDatos_sensor_temperatura() {
        return datos_sensor_temperatura;
    }

    public void setDatos_sensor_temperatura(double datos_sensor_temperatura) {
        this.datos_sensor_temperatura = datos_sensor_temperatura;
    }

    public double getDatos_sensor_conductividad() {
        return datos_sensor_conductividad;
    }

    public void setDatos_sensor_conductividad(double datos_sensor_conductividad) {
        this.datos_sensor_conductividad = datos_sensor_conductividad;
    }

    public double getDatos_sensor_nivel_agua() {
        return datos_sensor_nivel_agua;
    }

    public void setDatos_sensor_nivel_agua(double datos_sensor_nivel_agua) {
        this.datos_sensor_nivel_agua = datos_sensor_nivel_agua;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
