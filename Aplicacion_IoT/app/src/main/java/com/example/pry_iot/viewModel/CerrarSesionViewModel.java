package com.example.pry_iot.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pry_iot.repository.LogoutRepository;

public class CerrarSesionViewModel extends ViewModel {
    private final LogoutRepository repository;
    private final MutableLiveData<String> regpaqResponseLiveData = new MutableLiveData<>();

    public CerrarSesionViewModel() {
        repository = new LogoutRepository();
    }

    public void setToken(String token) {
        repository.setToken(token);
    }

    public LiveData<String> registerPackage(String email) {
        repository.registerPackage(email).observeForever(response -> {
            if (response != null) {
                regpaqResponseLiveData.setValue(response);
            } else {
                regpaqResponseLiveData.setValue("Error en el registro: Respuesta nula");
            }
        });

        return regpaqResponseLiveData;
    }
}

