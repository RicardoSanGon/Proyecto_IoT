package com.example.pry_iot.repositorios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.response.ReguserResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private ApiRequest apiService;

    public RegisterRepository() {
        apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<String> registerUser(Reguser reguser) {
        MutableLiveData<String> registerResult = new MutableLiveData<>();

        Call<ReguserResponse> call = apiService.registerUser(reguser);
        call.enqueue(new Callback<ReguserResponse>() {
            @Override
            public void onResponse(Call<ReguserResponse> call, Response<ReguserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMsg();
                    registerResult.setValue(message);
                } else {
                    registerResult.setValue("Error en el registro");
                }
            }
            @Override
            public void onFailure(Call<ReguserResponse> call, Throwable t) {
                registerResult.setValue("Error de red: " + t.getMessage());
            }
        });

        return registerResult;
    }
}
