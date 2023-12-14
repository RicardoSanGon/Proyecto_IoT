package com.example.pry_iot.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pry_iot.R;
import com.example.pry_iot.adapter.PaqueteAdapter;
import com.example.pry_iot.model.Paquete;
import com.example.pry_iot.viewmodel.CerrarSesionViewModel;
import com.example.pry_iot.viewmodel.PaqueteViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class rvpaquete extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PaqueteAdapter paqueteAdapter;
    private ArrayList<Paquete> paqueteArrayList = new ArrayList<>();
    private Button registroPaquetes;
    private Button btnCerrarSesion;
    private CerrarSesionViewModel cerrarSesionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvpaquete);
        registroPaquetes = findViewById(R.id.Registarpaquetes);


        registroPaquetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rvpaquete.this, RegistroPaquetes.class);
                startActivityForResult(intent, 1);
            }
        });
        btnCerrarSesion = findViewById(R.id.cerrarsesion);
        initialization();
        getPaquetes();

        cerrarSesionViewModel = new ViewModelProvider(this).get(CerrarSesionViewModel.class);

        btnCerrarSesion.setOnClickListener(view -> {
            String token = obtenerToken();
            String correo = obtenerCorreoDesdeSharedPreferences();


            if (!token.isEmpty()) {
                cerrarSesionViewModel.setToken(token);

                cerrarSesionViewModel.registerPackage(correo).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if(response.equals("LogOut Successfully")){
                        borrarDatosDeSharedPreferences();

                        Log.d(TAG, "Token después de borrar: " + obtenerToken());
                        Log.d(TAG, "Email después de borrar: " + obtenerCorreoDesdeSharedPreferences());
                        Intent intent = new Intent(rvpaquete.this, log_in.class);
                        startActivity(intent);
                        finish();;
                    }
                });
            } else {
                showToast("Token no disponible");
            }
        });

    }

    private String obtenerToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("token", "");
    }


    private void getPaquetes() {
        String token = obtenerToken();

        if (token.isEmpty()) {
            // Manejar el caso en que el token esté vacío
            Toast.makeText(this, "Token no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        PaqueteViewModel paqueteViewModel = new ViewModelProvider(this).get(PaqueteViewModel.class);

        paqueteViewModel.setToken(token);


        paqueteViewModel.getPaquetes().observe(this, paqueteResponse -> {
            if (paqueteResponse != null && paqueteResponse.getData() != null) {
                List<Paquete> paquetes = paqueteResponse.getData();

                paqueteArrayList.clear();
                paqueteArrayList.addAll(paquetes);
                paqueteAdapter.setPaquetes(paqueteArrayList);
            } else {
                Toast.makeText(this, "Error al obtener los paquetes", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initialization() {
        recyclerView = findViewById(R.id.rvpaquetes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paqueteAdapter = new PaqueteAdapter(paqueteArrayList);
        recyclerView.setAdapter(paqueteAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            recreate();
        }
    }
    private String obtenerCorreoDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("email", "");
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void borrarDatosDeSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString("token", "").putString("email", "").apply();
    }

}