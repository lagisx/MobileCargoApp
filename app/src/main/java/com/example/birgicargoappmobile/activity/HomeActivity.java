package com.example.birgicargoappmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import com.example.birgicargoappmobile.adapter.CargoAdapter;
import com.example.birgicargoappmobile.SupabaseCargoApi;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView cargoRecyclerView;
    private SupabaseCargoApi cargoApi;
    private CargoAdapter adapter;
    private List<Cargo> cargoList = new ArrayList<>();
    private int currentUserId;
    private CargoAdapter.OnCargoDeleteListener deleteListener;
    private boolean showingMyCargo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        cargoRecyclerView = findViewById(R.id.cargo_recycler_view);
        cargoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUserId = getIntent().getIntExtra("user_id", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cargoApi = retrofit.create(SupabaseCargoApi.class);

        deleteListener = cargo -> {
            String queryId = "eq." + cargo.getId();
            cargoApi.deleteCargoById(queryId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(HomeActivity.this, "Груз удалён", Toast.LENGTH_SHORT).show();
                    loadMyCargo();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                }
            });
        };

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_my_cargo) {
                loadMyCargo();
            } else if (id == R.id.nav_all_cargo) {
                loadAllCargo();
            } else if (id == R.id.nav_add_cargo) {
                Intent intent = new Intent(HomeActivity.this, AddCargoActivity.class);
                intent.putExtra("user_id", currentUserId);
                startActivityForResult(intent, 1);
            }
            drawerLayout.closeDrawers();
            return true;
        });

        View headerView = navigationView.getHeaderView(0);
        Button btnLogout = headerView.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Вы вышли из системы", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        loadAllCargo();
    }

    private void loadAllCargo() {
        showingMyCargo = false;
        cargoApi.getAllCargo().enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new CargoAdapter(response.body(), currentUserId, deleteListener, false);
                    cargoRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMyCargo() {
        showingMyCargo = true;
        String filter = "eq." + currentUserId;
        cargoApi.getCargoByCustomer(filter).enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new CargoAdapter(response.body(), currentUserId, deleteListener, true);
                    cargoRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Ошибка загрузки моих грузов", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (showingMyCargo) {
                loadMyCargo();
            } else {
                loadAllCargo();
            }
        }
    }
}
