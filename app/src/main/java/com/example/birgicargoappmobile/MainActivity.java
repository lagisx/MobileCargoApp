package com.example.birgicargoappmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // Инициализация элементов UI
        loginInput = findViewById(R.id.login_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        guestButton = findViewById(R.id.guest_button);
        registerLink = findViewById(R.id.register_link);

        // Обработчик кнопки авторизации
        loginButton.setOnClickListener(v -> handleLogin());

        // Обработчик кнопки входа как гост
        guestButton.setOnClickListener(v -> handleGuestLogin());

        // Обработчик ссылки регистрации
        registerLink.setOnClickListener(v -> goToRegister());
    }

    private void handleLogin() {
        String login = loginInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Валидация
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

        // TODO: Проверить учётные данные в БД
        // Для теста пока просто переходим на панель пользователя
        Toast.makeText(this, "Авторизация: " + login, Toast.LENGTH_SHORT).show();

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
        // Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        // startActivity(intent);
        Toast.makeText(this, "Переход на регистрацию", Toast.LENGTH_SHORT).show();
    }
}
