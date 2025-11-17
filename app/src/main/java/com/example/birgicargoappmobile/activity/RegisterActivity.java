package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birgicargoappmobile.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameInput;
    private EditText emailInput;
    private EditText phoneInput;
    private EditText passwordInput;
    private Button registerButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        fullNameInput = findViewById(R.id.full_name_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        loginLink = findViewById(R.id.login_link);

        registerButton.setOnClickListener(v -> handleRegister());
        loginLink.setOnClickListener(v -> goToLogin());
    }

    private void handleRegister() {
        String name = fullNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String pass = passwordInput.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(this, "Введите корректный Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty() || phone.length() < 10) {
            Toast.makeText(this, "Введите корректный номер", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 8) {
            Toast.makeText(this, "Пароль должен быть не менее 8 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Аккаунт создан!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    private void goToLogin() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}
