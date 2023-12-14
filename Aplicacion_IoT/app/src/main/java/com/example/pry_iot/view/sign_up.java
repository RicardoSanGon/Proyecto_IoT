package com.example.pry_iot.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pry_iot.R;
import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.viewmodel.RegisterViewModel;

public class sign_up extends AppCompatActivity {
    private EditText etNombre, etEmail, etPassword, etConfirmPassword;
    private TextView iniciaSesion;
    private Button btnRegister;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        etNombre = findViewById(R.id.etusuario);
        etEmail = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etcontra);
        etConfirmPassword = findViewById(R.id.etcontra_confirmacion);
        btnRegister = findViewById(R.id.registrar);
        iniciaSesion = findViewById(R.id.inicioSesionVista);

        iniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, log_in.class);
                startActivity(intent);
                finish();
            }
        });

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        btnRegister.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String pass_Conf = etConfirmPassword.getText().toString();

            clearErrors();

            Reguser reguser = new Reguser();
            reguser.setNombre(nombre);
            reguser.setEmail(email);
            reguser.setPassword(password);
            reguser.setPassword_confirmation(pass_Conf);

            if (reguser.isValid()) {
                disableEditTextFocus();
                View loadingView = getLayoutInflater().inflate(R.layout.progressbar, null);

                final Dialog loadingDialog = new Dialog(sign_up.this);
                loadingDialog.setContentView(loadingView);
                Window window = loadingDialog.getWindow();

                if (window != null) {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                    window.setAttributes(layoutParams);
                }
                loadingDialog.setCancelable(false);
                loadingDialog.show();

                registerViewModel.registerUser(reguser).observe(this, result -> {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                    if (result.equals("Usuario creado correctamente")) {
                        Intent intent = new Intent(sign_up.this, log_in.class);
                        startActivity(intent);
                        loadingDialog.dismiss();
                        finish();
                    } else {
                        enableEditTextFocus();
                        loadingDialog.dismiss();
                    }
                });
            } else {
                enableEditTextFocus();
                showValidationErrors(reguser);
            }
        });
    }
    private void clearErrors() {
        etNombre.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
    }
    private void showValidationErrors(Reguser reguser) {
        if (!reguser.isNombreValid()) {
            etNombre.setError("Campo requerido");
        }

        if (!reguser.isEmailValid()) {
            etEmail.setError("Correo electrónico no válido");
        }

        if (!reguser.isPasswordValid()) {
            etPassword.setError("Campo requerido");
        }

        if (!reguser.isConfirmPasswordValid()) {
            etConfirmPassword.setError("Las contraseñas no coinciden");
        }
    }
    private void disableEditTextFocus() {
        etNombre.setFocusable(false);
        etEmail.setFocusable(false);
        etPassword.setFocusable(false);
        etConfirmPassword.setFocusable(false);

        etNombre.invalidate();
        etEmail.invalidate();
        etPassword.invalidate();
        etConfirmPassword.invalidate();
    }

    private void enableEditTextFocus() {
        etNombre.setFocusable(true);
        etEmail.setFocusable(true);
        etPassword.setFocusable(true);
        etConfirmPassword.setFocusable(true);

        etNombre.setFocusableInTouchMode(true);
        etEmail.setFocusableInTouchMode(true);
        etPassword.setFocusableInTouchMode(true);
        etConfirmPassword.setFocusableInTouchMode(true);

        etNombre.invalidate();
        etEmail.invalidate();
        etPassword.invalidate();
        etConfirmPassword.invalidate();
    }
}
