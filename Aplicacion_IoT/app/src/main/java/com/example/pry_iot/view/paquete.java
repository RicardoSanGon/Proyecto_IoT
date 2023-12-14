package com.example.pry_iot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pry_iot.R;
import com.example.pry_iot.model.Paquete;
import com.example.pry_iot.retrofit.ApiRequest;
import com.example.pry_iot.retrofit.RetrofitRequest;
import com.example.pry_iot.viewmodel.FocoViewModel;

public class paquete extends AppCompatActivity implements View.OnClickListener {
    TextView ph, turbidez, temperatura, conductividad, nivel_agua;
    Button btnhistorial;
    private Paquete paquete;
    private ApiRequest apiRequest= RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    private Handler handler;
    private static final String TAG = paquete.class.getSimpleName();
    private Runnable updateRunnable;
    private String token;
    private FocoViewModel focoViewModel;
    ImageView foco;
    private boolean focoEncendido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paquete);
        token = obtenerToken();
        ph = findViewById(R.id.ph);
        turbidez = findViewById(R.id.turbidez);
        temperatura = findViewById(R.id.temperatura);
        conductividad = findViewById(R.id.conductividad);
        nivel_agua = findViewById(R.id.nivel_agua);
        btnhistorial = findViewById(R.id.btnhistorial);
        btnhistorial.setOnClickListener(this);

        if (getIntent().hasExtra("paquete")) {
            paquete = (Paquete) getIntent().getSerializableExtra("paquete");
            ph.setText(String.valueOf(paquete.getDatos_sensor_ph()));
            turbidez.setText(String.valueOf(paquete.getDatos_sensor_turbidez()));
            temperatura.setText(String.valueOf(paquete.getDatos_sensor_temperatura()));
            conductividad.setText(String.valueOf(paquete.getDatos_sensor_conductividad()));
            nivel_agua.setText(String.valueOf(paquete.getDatos_sensor_nivel_agua()));
        }
        iniciarRepeticion();

        focoViewModel = new FocoViewModel(this);

        foco = findViewById(R.id.foco);
        foco.setOnClickListener(view -> {
            focoEncendido = !focoEncendido;
            int estadoFoco = focoEncendido ? 1 : 0;
            focoViewModel.cambiarEstadoFoco(estadoFoco);

            if (focoEncendido) {
                showToast("El foco está encendido");
                foco.setImageResource(R.drawable.img_1);
            } else {
                showToast("El foco está apagado");
                foco.setImageResource(R.drawable.img_2);
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (paquete != null) {
            Intent intent = new Intent(v.getContext(), rvhistorial_paq.class);
            intent.putExtra("id", paquete.getId());
            v.getContext().startActivity(intent);
        }
    }

    private void iniciarRepeticion() {
        handler = new Handler(Looper.getMainLooper());
        updateRunnable=new Runnable() {
            @Override
            public void run() {
                apiRequest.getUpdatePack("Bearer "+token,paquete.getId()).enqueue(new retrofit2.Callback<com.example.pry_iot.response.UpdatePackResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.example.pry_iot.response.UpdatePackResponse> call, retrofit2.Response<com.example.pry_iot.response.UpdatePackResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                ph.setText(String.valueOf(response.body().getData().getDatos_sensor_ph()));
                                turbidez.setText(String.valueOf(response.body().getData().getDatos_sensor_turbidez()));
                                temperatura.setText(String.valueOf(response.body().getData().getDatos_sensor_temperatura()));
                                conductividad.setText(String.valueOf(response.body().getData().getDatos_sensor_conductividad()));
                                nivel_agua.setText(String.valueOf(response.body().getData().getDatos_sensor_nivel_agua()));
                            }
                        } else {
                            Log.e(TAG, "Error en la solicitud: " + response.code() + " - " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<com.example.pry_iot.response.UpdatePackResponse> call, Throwable t) {
                        Log.e(TAG, "Error en la solicitud: " + t.getMessage());
                    }
                });
                handler.postDelayed(this, 30000);
            }
        };
        handler.postDelayed(updateRunnable, 20000);
    }


    @Override
    protected void onDestroy() {
        // Detiene la repetición cuando la actividad se destruye
        detenerRepeticion();
        super.onDestroy();
    }

    private void detenerRepeticion() {
        if (handler != null && updateRunnable != null) {
            handler.removeCallbacks(updateRunnable);
        }
    }
    private String obtenerToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("token", "");
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}