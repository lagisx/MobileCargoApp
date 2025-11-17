package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.SupabaseUsersApi;
import com.example.birgicargoappmobile.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText loginInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button guestButton;
    private TextView registerLink;
    private SupabaseUsersApi supabaseUsersApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInput = findViewById(R.id.login_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        guestButton = findViewById(R.id.guest_button);
        registerLink = findViewById(R.id.register_link);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        supabaseUsersApi = retrofit.create(SupabaseUsersApi.class);

        loginButton.setOnClickListener(v -> handleLogin());
        guestButton.setOnClickListener(v -> handleGuestLogin());
        registerLink.setOnClickListener(v -> goToRegister());
    }

    private void handleLogin() {
        String login = loginInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        Log.d(TAG, "=== LOGIN ATTEMPT ===");
        Log.d(TAG, "Login: " + login);
        Log.d(TAG, "Password: " + password);

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login.length() < 3) {
            Toast.makeText(this, "Логин должен быть от 3 символов!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "Пароль должен быть не менее 8 символов!", Toast.LENGTH_SHORT).show();
            return;
        }

        supabaseUsersApi.getUserByLogin("eq." + login).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d(TAG, "=== RESPONSE ===");
                Log.d(TAG, "Code: " + response.code());
                Log.d(TAG, "Body: " + response.body());

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    User user = response.body().get(0);

                    if (user.getPassword() != null && user.getPassword().equals(password)) {
                        Toast.makeText(MainActivity.this, "Добро пожаловать, " + login + "!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("username", login);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Неправильный пароль!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Пользователь не найден!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "=== ERROR === " + t.getMessage());
                Toast.makeText(MainActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleGuestLogin() {
        Toast.makeText(this, "Вход как гость", Toast.LENGTH_SHORT).show();
    }
    private void goToRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
