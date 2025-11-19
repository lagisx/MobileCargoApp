package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.SupabaseUsersApi;
import com.example.birgicargoappmobile.model.User;

import java.util.List;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
public class ProfileActivity extends AppCompatActivity {

    private EditText editLogin, editPassword;
    private Button btnSave;
    private SupabaseUsersApi usersApi;
    private int userId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editLogin = findViewById(R.id.edit_login);
        editPassword = findViewById(R.id.edit_password);
        btnSave = findViewById(R.id.btn_save);

        userId = getIntent().getIntExtra("user_id", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usersApi = retrofit.create(SupabaseUsersApi.class);

        loadUser();

        btnSave.setOnClickListener(v -> updateUser());
    }

    private void loadUser() {
        usersApi.getUserById("eq." + userId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    currentUser = response.body().get(0);
                    editLogin.setText(currentUser.getLogin());
                    editPassword.setText(currentUser.getPassword());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        currentUser.setLogin(editLogin.getText().toString());
        currentUser.setPassword(editPassword.getText().toString());

        usersApi.updateUserById("eq." + userId, currentUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updated_login", currentUser.getLogin());
                    setResult(RESULT_OK, resultIntent);

                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
