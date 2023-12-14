package com.example.pry_iot.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pry_iot.model.Regpaq;
import com.example.pry_iot.repository.RegPaquetesRepository;

public class RegPaquetesViewModel extends ViewModel {
    private final RegPaquetesRepository repository;
    private final MutableLiveData<String> regpaqResponseLiveData = new MutableLiveData<>();

    public RegPaquetesViewModel() {
        repository = new RegPaquetesRepository();
    }

    public void setToken(String token) {
        repository.setToken(token);
    }

    public LiveData<String> registerPackage(Regpaq regpaq) {
        repository.registerPackage(regpaq).observeForever(response -> {
            if (response != null) {
                regpaqResponseLiveData.setValue(response);
            } else {
                regpaqResponseLiveData.setValue("Error en el registro: Respuesta nula");
            }
        });

        return regpaqResponseLiveData;
    }
}
