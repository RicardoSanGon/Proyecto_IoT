package com.example.pry_iot.model;

import android.text.TextUtils;
import android.util.Patterns;

public class Reguser {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private String password_confirmation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }


    //validaciones
    public boolean isValid() {
        return isNombreValid() && isEmailValid() && isPasswordValid() && isConfirmPasswordValid();
    }

    public boolean isNombreValid() {
        return !TextUtils.isEmpty(nombre);
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPasswordValid() {
        return !TextUtils.isEmpty(password);
    }

    public boolean isConfirmPasswordValid() {
        return password.equals(password_confirmation);
    }
}
