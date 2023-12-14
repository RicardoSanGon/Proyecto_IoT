package com.example.pry_iot.view;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.pry_iot.R;
import com.example.pry_iot.adapter.HistorialAdapter;
import com.example.pry_iot.model.Historial_paquete;
import com.example.pry_iot.response.HistorialResponse;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class rvhistorial_paq extends AppCompatActivity {
    RecyclerView rvHistorial;
    Intent intent;
    ApiRequest apiRequest= RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    private final String TAG = rvhistorial_paq.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvhistorial_paq);
        rvHistorial = findViewById(R.id.rvHistorial);
        intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String token = obtenerToken();
        if (id!=0){
            apiRequest.getHistorial("Bearer "+token,id).enqueue(new Callback<HistorialResponse>() {
                @Override
                public void onResponse(Call<HistorialResponse> call, Response<HistorialResponse> response) {
                    if (response.isSuccessful()){
                        HistorialResponse historialResponses = response.body();
                        List<Historial_paquete> historial_paquetes = historialResponses.getData();
                        HistorialAdapter historialAdapter = new HistorialAdapter(historial_paquetes);
                        rvHistorial.setAdapter(historialAdapter);
                        rvHistorial.setLayoutManager(new LinearLayoutManager(rvhistorial_paq.this));
                        rvHistorial.setHasFixedSize(true);
                        rvHistorial.setItemViewCacheSize(historial_paquetes.size()-1);
                    }
                    else{
                        Toast.makeText(rvhistorial_paq.this, String.valueOf(id)+"jeje", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<HistorialResponse> call, Throwable t) {
                    Log.e(TAG, "Error en la solicitud: " + t.getMessage());
                }
            });
        }
        else{
            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private String obtenerToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("token", "");
    }
}