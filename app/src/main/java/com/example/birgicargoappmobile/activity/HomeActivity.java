package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.birgicargoappmobile.R;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeText;
    private TextView userText;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcome_text);
        userText = findViewById(R.id.user_text);
        logoutButton = findViewById(R.id.logout_button);

        String login = getIntent().getStringExtra("username");
        if (login != null) {
            userText.setText("Пользователь: " + login);
        }

        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });
    }
}
