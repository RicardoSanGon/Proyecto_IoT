package com.example.pry_iot.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.example.pry_iot.response.FocoResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import retrofit2.Callback;


public class FocoRepository {
    private ApiRequest apiService;

    public FocoRepository() {
        this.apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void actualizarEstadoFoco(String token, int valor, Callback<FocoResponse> callback) {
        try {
            Log.d(TAG, "Actualizando estado del foco: " + valor);
            Log.d(TAG, "Actualizando estado del foco: " + token);
            Log.d(TAG, "Actualizando estado del foco: " + callback);
            apiService.encenderFoco(token, valor).enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, "Excepción al realizar la llamada a la API", e);
        }
    }
}
