package com.example.pry_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.response.ReguserResponse;
import com.example.pry_iot.viewModel.RegisterViewModel;

public class sign_up extends AppCompatActivity {
    private EditText etNombre, etEmail, etPassword, etConfirmPassword;
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
        btnRegister = findViewById(R.id.btninicarsesion);

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
                registerViewModel.registerUser(reguser).observe(this, result -> {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    if (result.equals("Usuario creado correctamente")) {
                        Intent intent = new Intent(sign_up.this, SignIn.class);
                        startActivity(intent);
                        finish();
                    }else {
                        enableEditTextFocus();
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
