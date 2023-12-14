package com.example.pry_iot.viewmodel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.pry_iot.repository.FocoRepository;
import com.example.pry_iot.response.FocoResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FocoViewModel extends ViewModel {

    private final FocoRepository focoRepository;
    private final Context context;
    public FocoViewModel(Context context) {
        this.context = context;
        this.focoRepository = new FocoRepository();
    }
    private String obtenerToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("token", "");
    }

    public void cambiarEstadoFoco(int estado) {
        String token = obtenerToken();
            focoRepository.actualizarEstadoFoco("Bearer " + token, estado, new Callback<FocoResponse>() {
                @Override
                public void onResponse(Call<FocoResponse> call, Response<FocoResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e(TAG, "Error en la API: " + errorBody);
                            Log.d(TAG, "Respuesta completa de la API: " + response.toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<FocoResponse> call, Throwable t) {
                    Log.e(TAG, "Error en la llamada a la API", t);
                }
            });
    }
}
