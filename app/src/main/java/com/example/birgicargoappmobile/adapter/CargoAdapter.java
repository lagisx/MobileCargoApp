package com.example.birgicargoappmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import java.util.List;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.CargoViewHolder> {

    private List<Cargo> cargoList;

    public CargoAdapter(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    @NonNull
    @Override
    public CargoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cargo_item, parent, false);
        return new CargoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoViewHolder holder, int position) {
        Cargo cargo = cargoList.get(position);

        // Заполняем все поля карточки
        holder.title.setText(cargo.getProduct());
        holder.id.setText("#" + cargo.getId());
        holder.description.setText(
                "ТС: " + cargo.getVehicleType() +
                        " | Вес: " + cargo.getWeight() + "т" +
                        " | Объём: " + cargo.getVolume() + "м³");
        holder.from.setText(cargo.getFromLocation());
        holder.to.setText(cargo.getToLocation());
        holder.date.setText(cargo.getDates());

        // ВАЖНО: заполняем телефон и цену
        holder.phone.setText(cargo.getContactPhone());
        holder.price.setText("₽ " + String.format("%.0f", cargo.getPriceByCard()));
    }

    @Override
    public int getItemCount() {
        return cargoList.size();
    }

    static class CargoViewHolder extends RecyclerView.ViewHolder {
        TextView title, id, description, from, to, date, phone, price;

        CargoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cargo_title);
            id = itemView.findViewById(R.id.cargo_id);
            description = itemView.findViewById(R.id.cargo_description);
            from = itemView.findViewById(R.id.cargo_from);
            to = itemView.findViewById(R.id.cargo_to);
            date = itemView.findViewById(R.id.cargo_date);

            // ВАЖНО: присваиваем переменным поля из layout
            phone = itemView.findViewById(R.id.cargo_phone);
            price = itemView.findViewById(R.id.cargo_price);
        }
    }

    public void setCargoList(List<Cargo> newCargoList) {
        this.cargoList = newCargoList;
        notifyDataSetChanged();
    }
}
