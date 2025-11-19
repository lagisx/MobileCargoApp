package com.example.birgicargoappmobile.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import com.example.birgicargoappmobile.model.User;
import com.example.birgicargoappmobile.SupabaseCargoApi;
import com.example.birgicargoappmobile.SupabaseUsersApi;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class AddCargoActivity extends AppCompatActivity {

    private EditText etVehicleType, etWeight, etVolume, etProduct, etFromLocation, etToLocation;
    private EditText etLoadingType, etLoadingDetails, etDates, etPriceByCard, etPriceWithVat, etBargain;
    private Button btnAddCargo;
    private SupabaseCargoApi cargoApi;
    private SupabaseUsersApi usersApi;
    private int currentUserId;
    private String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cargo);

        currentUserId = getIntent().getIntExtra("user_id", -1);
        if (currentUserId == -1) {
            Toast.makeText(this, "Ошибка: пользователь не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etVehicleType = findViewById(R.id.et_vehicle_type);
        etWeight = findViewById(R.id.et_weight);
        etVolume = findViewById(R.id.et_volume);
        etProduct = findViewById(R.id.et_product);
        etFromLocation = findViewById(R.id.et_from_location);
        etToLocation = findViewById(R.id.et_to_location);
        etLoadingType = findViewById(R.id.et_loading_type);
        etLoadingDetails = findViewById(R.id.et_loading_details);
        etDates = findViewById(R.id.et_dates);
        etPriceByCard = findViewById(R.id.et_price_by_card);
        etPriceWithVat = findViewById(R.id.et_price_with_vat);
        etPriceWithVat.setEnabled(false);;
        etBargain = findViewById(R.id.et_bargain);
        btnAddCargo = findViewById(R.id.btn_add_cargo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mkdwltdoayuhuikzycod.supabase.co/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cargoApi = retrofit.create(SupabaseCargoApi.class);
        usersApi = retrofit.create(SupabaseUsersApi.class);

        loadUserPhone();

        etPriceByCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String priceStr = s.toString().trim();
                if (!priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        double priceWithVat = price * 1.20;
                        etPriceWithVat.setText(String.format("%.2f", priceWithVat));
                    } catch (NumberFormatException e) {
                        etPriceWithVat.setText("");
                    }
                } else {
                    etPriceWithVat.setText("");
                }
            }
        });

        btnAddCargo.setOnClickListener(v -> addCargo());
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void loadUserPhone() {
        String filter = "eq." + currentUserId;
        usersApi.getUserById(filter).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    User user = response.body().get(0);
                    userPhone = user.getPhone() != null ? user.getPhone() : "";
                    Toast.makeText(AddCargoActivity.this, "Профиль загружен: " + userPhone, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) { }
        });
    }
    private void addCargo() {
        if (etProduct.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните название товара", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etFromLocation.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните откуда", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etToLocation.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните куда", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etVehicleType.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните Тип транспортного средства", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etWeight.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните вес", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etVolume.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните объём", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etFromLocation.getText().toString().trim().isEmpty() || etToLocation.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните маршрут", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etLoadingType.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните Тип загрузки", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDates.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните дату", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etPriceByCard.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Заполните цену наличными", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = parseDouble(etWeight.getText().toString());
        double volume = parseDouble(etVolume.getText().toString());
        double priceByCard = parseDouble(etPriceByCard.getText().toString());
        double priceWithVat = priceByCard * 1.20;

        if (priceByCard == 0) {
            Toast.makeText(this, "Цена не может быть 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (weight == 0) {
            Toast.makeText(this, "Вес не может быть 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (volume == 0) {
            Toast.makeText(this, "Объём не может быть 0", Toast.LENGTH_SHORT).show();
            return;
        }

        Cargo cargo = new Cargo();
        cargo.setProduct(etProduct.getText().toString().trim());
        cargo.setFromLocation(etFromLocation.getText().toString().trim());
        cargo.setToLocation(etToLocation.getText().toString().trim());
        cargo.setVehicleType(etVehicleType.getText().toString().trim());
        cargo.setWeight(parseDouble(etWeight.getText().toString()));
        cargo.setVolume(parseDouble(etVolume.getText().toString()));
        cargo.setLoadingType(etLoadingType.getText().toString().trim());
        cargo.setLoadingDetails(etLoadingDetails.getText().toString().trim());
        cargo.setDates(etDates.getText().toString().trim());
        cargo.setPriceByCard(priceByCard);
        cargo.setPriceWithVat(priceWithVat);
        cargo.setBargainOrNoBargain(etBargain.getText().toString().trim());
        cargo.setContactPhone(userPhone.isEmpty() ? "+" : userPhone);
        cargo.setCustomerId(currentUserId);

        btnAddCargo.setEnabled(false);
        btnAddCargo.setText("Загрузка...");

        cargoApi.addCargo(cargo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                btnAddCargo.setEnabled(true);
                btnAddCargo.setText("Добавить груз");
                if (response.isSuccessful()) {
                    Toast.makeText(AddCargoActivity.this, "Груз добавлен успешно!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    String errorBody = null;
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {}
                    Toast.makeText(AddCargoActivity.this,
                            "Ошибка " + response.code() + ": " + (errorBody != null ? errorBody : response.message()),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                btnAddCargo.setEnabled(true);
                btnAddCargo.setText("Добавить груз");
                Toast.makeText(AddCargoActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private double parseDouble(String value) {
        try {
            return value.trim().isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
