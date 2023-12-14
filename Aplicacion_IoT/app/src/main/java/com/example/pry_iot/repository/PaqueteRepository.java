package com.example.pry_iot.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pry_iot.response.PaqueteResponse;
import com.example.pry_iot.response.UserResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaqueteRepository {
    private static final String TAG = PaqueteRepository.class.getSimpleName();
    private ApiRequest apiRequest;
    private String token;

    public PaqueteRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void setToken(String token) {
        this.token = token;
        Log.d(TAG, "Token establecido en PaqueteRepository: " + token);
    }

    private boolean isTokenAvailable() {
        return token != null && !token.isEmpty();
    }

    public LiveData<PaqueteResponse> getPaquetes() {
        final MutableLiveData<PaqueteResponse> data = new MutableLiveData<>();

        if (!isTokenAvailable()) {
            Log.e(TAG, "Token no disponible. No se puede realizar la solicitud.");
            data.setValue(null);
            return data;
        }

        Log.d(TAG, "Token utilizado en la solicitud: " + token);

        apiRequest.getPaquetes("Bearer " + token).enqueue(new Callback<PaqueteResponse>() {
            @Override
            public void onResponse(Call<PaqueteResponse> call, Response<PaqueteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    }
                } else {
                    Log.e(TAG, "Error en la solicitud: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PaqueteResponse> call, Throwable t) {
                Log.e(TAG, "Error en la solicitud: " + t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}




