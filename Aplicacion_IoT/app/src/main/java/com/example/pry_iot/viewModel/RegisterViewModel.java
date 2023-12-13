package com.example.pry_iot.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.repositorios.RegisterRepository;
import com.example.pry_iot.response.ReguserResponse;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository registerRepository = new RegisterRepository();
    private MutableLiveData<String> registerResult = new MutableLiveData<>();

    public LiveData<String> registerUser(Reguser reguser) {
        if (reguser.isValid()) {
            registerRepository.registerUser(reguser).observeForever(response -> {
                if (response != null) {
                    registerResult.setValue(response);
                } else {
                    registerResult.setValue("Error en el registro: Respuesta nula");
                }
            });
        } else {
            registerResult.setValue("Error en el registro: Datos de registro no válidos");
        }
        return registerResult;
    }
}
