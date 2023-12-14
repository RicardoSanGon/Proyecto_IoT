package com.example.pry_iot.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pry_iot.repository.PaqueteRepository;
import com.example.pry_iot.response.PaqueteResponse;

public class PaqueteViewModel extends AndroidViewModel {
    private PaqueteRepository paqueteRepository;

    public PaqueteViewModel(Application application) {
        super(application);
        paqueteRepository = new PaqueteRepository();
    }

    public void setToken(String token) {
        paqueteRepository.setToken(token);
    }

    public LiveData<PaqueteResponse> getPaquetes() {
        return paqueteRepository.getPaquetes();
    }
}




