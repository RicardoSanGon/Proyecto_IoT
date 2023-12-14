package com.example.pry_iot.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pry_iot.R;
import com.example.pry_iot.model.Regpaq;
import com.example.pry_iot.viewmodel.RegPaquetesViewModel;

public class RegistroPaquetes extends AppCompatActivity {
    private EditText etNombrePaquete, etLugarPaquete;
    private Button btnRegistrarPaquete;
    private RegPaquetesViewModel packageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paquetes);

        etNombrePaquete = findViewById(R.id.etnombrepaquete);
        etLugarPaquete = findViewById(R.id.etlugar);
        btnRegistrarPaquete = findViewById(R.id.btnagregar);

        packageViewModel = new ViewModelProvider(this).get(RegPaquetesViewModel.class);

        btnRegistrarPaquete.setOnClickListener(view -> {
            String nombrePaquete = etNombrePaquete.getText().toString();
            String lugarPaquete = etLugarPaquete.getText().toString();

            if (nombrePaquete.isEmpty() || lugarPaquete.isEmpty()) {
                showToast("Por favor, completa todos los campos");
                return;
            }

            Regpaq regpaq = new Regpaq();
            regpaq.setNombre_paquete(nombrePaquete);
            regpaq.setLugar_paquete(lugarPaquete);

            String token = getTokenFromSharedPreferences();

            if (!token.isEmpty()) {
                packageViewModel.setToken(token);

                packageViewModel.registerPackage(regpaq).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if(response.equals("Paquete creado con exito")){
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();;
                    }
                });
            } else {
                showToast("Token no disponible");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getTokenFromSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("token", "");
    }
}
