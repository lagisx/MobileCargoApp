package com.example.birgicargoappmobile.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.navigation.NavigationView;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import com.example.birgicargoappmobile.adapter.CargoAdapter;
import com.example.birgicargoappmobile.SupabaseCargoApi;
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
        adapter = new CargoAdapter(cargoList);
        cargoRecyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cargoApi = retrofit.create(SupabaseCargoApi.class);

        currentUserId = getIntent().getIntExtra("user_id", -1);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_my_cargo) {
                loadMyCargo();
            } else if (id == R.id.nav_all_cargo) {
                loadAllCargo();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        loadAllCargo();
    }

    private void loadAllCargo() {
        cargoApi.getAllCargo().enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                Log.d("CARGO", "code=" + response.code());
                if (response.body() != null) {
                    Log.d("CARGO", "size=" + response.body().size());
                    for (Cargo cargo : response.body()) {
                        Log.d("CARGO", "product=" + cargo.getProduct() + " id=" + cargo.getId());
                    }
                } else {
                    Log.d("CARGO", "response body is null");
                }
                // adapter update
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setCargoList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {}
        });
    }

    private void loadMyCargo() {
        String filter = "eq." + currentUserId;
        cargoApi.getCargoByCustomer(filter).enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setCargoList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {}
        });
    }
}
