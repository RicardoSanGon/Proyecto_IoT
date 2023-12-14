package com.example.pry_iot.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pry_iot.response.UserResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private ApiRequest apiRequest;
    private Context context;

    public UserRepository(Context context) {
        this.context = context;
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getUser(String email, String password, MutableLiveData<UserResponse> userResponseLiveData) {
        apiRequest.login(email, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                handleLoginResponse(response, email, userResponseLiveData);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                handleErrorResponse(t, userResponseLiveData);
            }
        });
    }

    private void handleLoginResponse(Response<UserResponse> response, String email, MutableLiveData<UserResponse> userResponseLiveData) {
        if (response.isSuccessful()) {
            UserResponse userResponse = response.body();
            if (userResponse != null) {
                saveTokenAndEmailToSharedPreferences(context, userResponse.getToken(), email);
                Log.d(TAG, "Token y correo electrónico guardados en SharedPreferences");
                userResponse.setErrorType(null);
                userResponseLiveData.setValue(userResponse);
            }
        } else {
            handleErrorResponse(null, userResponseLiveData);
        }
    }

    private void handleErrorResponse(Throwable t, MutableLiveData<UserResponse> userResponseLiveData) {
        Log.d(TAG, "Error en la solicitud: " + (t != null ? t.getMessage() : "Unknown error"));
        UserResponse userResponse = new UserResponse();
        userResponse.setErrorType("network"); // Puedes ajustar esto según la lógica de tu aplicación
        userResponseLiveData.setValue(userResponse);
    }

    private void saveTokenAndEmailToSharedPreferences(Context context, String token, String email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.putString("email", email);
        editor.apply();
    }
}
