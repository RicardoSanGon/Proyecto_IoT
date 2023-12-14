package com.example.pry_iot.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pry_iot.R;
import com.example.pry_iot.viewmodel.LogInViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class log_in extends AppCompatActivity {

    private LogInViewModel logInViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView registrarse;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        // Inicializar vistas y ViewModel
        emailEditText = findViewById(R.id.etemail);
        passwordEditText = findViewById(R.id.etcontra);
        logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        registrarse = findViewById(R.id.tvregistrarse);


        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(log_in.this, sign_up.class);
                startActivity(intent);
                finish();
            }
        });

        // Manejar el clic del botón de inicio de sesión
        Button loginButton = findViewById(R.id.btninicarsesion);
        loginButton.setOnClickListener(view -> attemptLogin());
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Verificar si los campos no están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Manejar el caso en que los campos estén vacíos
            Toast.makeText(this, "Ingrese correo electrónico/contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al método para iniciar sesión con las credenciales proporcionadas
        logInViewModel.loginUser(email, password);
        // Observar los cambios en los datos del usuario
        logInViewModel.getUserResponseLiveData().observe(this, userResponse -> {
            if (userResponse != null) {
                if (userResponse.getErrorType() == null) {
                    Intent intent = new Intent(this, rvpaquete.class);
                    startActivity(intent);
                    finish();
                } else {
                    // El inicio de sesión falló, manejar el error del repositorio
                    handleRepositoryError(userResponse.getErrorType());
                }
            } else {
                // Manejar el caso donde userResponse es nulo
                Toast.makeText(this, "Error al iniciar sesión: Respuesta nula", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void handleRepositoryError(String errorType) {
        String errorMessage;
        // Asignar mensajes específicos según el tipo de error del repositorio
        switch (errorType) {
            case "password":
                errorMessage = "Contraseña incorrecta";
                break;
            case "network":
                errorMessage = "Ingrese sus datos correctamente";
                break;
            default:
                errorMessage = "Error desconocido";
                break;
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}



