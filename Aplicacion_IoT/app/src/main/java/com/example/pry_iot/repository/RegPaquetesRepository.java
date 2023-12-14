package com.example.pry_iot.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pry_iot.model.Regpaq;
import com.example.pry_iot.response.RegpaqResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegPaquetesRepository {
    private static final String TAG = RegPaquetesRepository.class.getSimpleName();
    private ApiRequest apiRequest;
    private String token;

    public RegPaquetesRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void setToken(String token) {
        this.token = token;
        Log.d(TAG, "Token establecido en RegPaquetesRepository: " + token);
    }

    private boolean isTokenAvailable() {
        return token != null && !token.isEmpty();
    }

    public LiveData<String> registerPackage(Regpaq regpaq) {
        final MutableLiveData<String> data = new MutableLiveData<>();

        if (!isTokenAvailable()) {
            Log.e(TAG, "Token no disponible. No se puede realizar la solicitud.");
            data.setValue("Error: Token no disponible");
            return data;
        }

        Log.d(TAG, "Token utilizado en la solicitud de registro de paquete: " + token);

        apiRequest.registrarPaquete("Bearer " + token, regpaq).enqueue(new Callback<RegpaqResponse>() {
            @Override
            public void onResponse(Call<RegpaqResponse> call, Response<RegpaqResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String message = response.body().getMsg();
                        data.setValue(message);
                    }
                } else {
                    Log.e(TAG, "Error en la solicitud de registro de paquete: " + response.code() + " - " + response.message());
                    data.setValue("Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RegpaqResponse> call, Throwable t) {
                Log.e(TAG, "Error en la solicitud de registro de paquete: " + t.getMessage());
                data.setValue("Error: " + t.getMessage());
            }
        });

        return data;
    }
}

