package com.example.birgicargoappmobile.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import com.example.birgicargoappmobile.adapter.CargoAdapter;
import com.example.birgicargoappmobile.SupabaseCargoApi;
import java.util.ArrayList;
import java.util.List;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuestViewActivity extends AppCompatActivity {
    private RecyclerView cargoRecyclerView;
    private SupabaseCargoApi cargoApi;
    private CargoAdapter adapter;
    private List<Cargo> cargoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Гостевой просмотр");
        }

        cargoRecyclerView = findViewById(R.id.cargo_recycler_view);
        cargoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cargoApi = retrofit.create(SupabaseCargoApi.class);

        loadAllCargo();
    }

    private void loadAllCargo() {
        cargoApi.getAllCargo().enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new CargoAdapter(response.body(), -1, null, false);
                    cargoRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                Toast.makeText(GuestViewActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
