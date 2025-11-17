package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.SupabaseUsersApi;
import com.example.birgicargoappmobile.model.User;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameInput, emailInput, phoneInput, passwordInput;
    private Button registerButton;
    private TextView loginLink;
    private SupabaseUsersApi supabaseUsersApi;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        supabaseUsersApi = retrofit.create(SupabaseUsersApi.class);

        registerButton.setOnClickListener(v -> handleRegister());
        loginLink.setOnClickListener(v -> goToLogin());
    }
    private void handleRegister() {
        String login = fullNameInput.getText().toString().trim().toLowerCase();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (login.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@")) {
            Toast.makeText(this, "Введите корректный Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "Пароль должен быть не менее 8 символов!", Toast.LENGTH_SHORT).show();
            return;
        }
        supabaseUsersApi.getUserByLogin("eq." + login).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Логин уже используется", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(0, login, email, phone, password,
                            LocalDateTime.now().toString(), false);

                    supabaseUsersApi.createUser(newUser).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 201) {
                                Toast.makeText(RegisterActivity.this, "Аккаунт создан успешно!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Ошибка регистрации! Код: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToLogin() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}
