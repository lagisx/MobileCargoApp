package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birgicargoappmobile.R;

public class MainActivity extends AppCompatActivity {
    private EditText loginInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button guestButton;
    private TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInput = findViewById(R.id.login_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        guestButton = findViewById(R.id.guest_button);
        registerLink = findViewById(R.id.register_link);

        loginButton.setOnClickListener(v -> handleLogin());
        guestButton.setOnClickListener(v -> handleGuestLogin());
        registerLink.setOnClickListener(v -> goToRegister());
    }

    private void handleLogin() {
        String login = loginInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (login.length() < 3) {
            Toast.makeText(this, "Логин должен быть от 3 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(this, "Пароль должен быть от 8 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Авторизация: " + login, Toast.LENGTH_SHORT).show();


        System.out.println(login);
        System.out.println(password);
        // Intent intent = new Intent(MainActivity.this, UserPanelActivity.class);
        // intent.putExtra("username", login);
        // startActivity(intent);
    }

    private void handleGuestLogin() {
        Toast.makeText(this, "Вход как гост", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(MainActivity.this, GuestPanelActivity.class);
        // startActivity(intent);
    }

    private void goToRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
