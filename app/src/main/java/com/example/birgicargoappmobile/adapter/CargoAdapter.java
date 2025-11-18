package com.example.birgicargoappmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.birgicargoappmobile.R;
import com.example.birgicargoappmobile.model.Cargo;
import java.util.List;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.CargoViewHolder> {

    private List<Cargo> cargoList;
    private int currentUserId;
    private OnCargoDeleteListener deleteListener;
    private boolean showDeleteButton;

    public interface OnCargoDeleteListener {
        void onDeleteClick(Cargo cargo);
    }

    public CargoAdapter(List<Cargo> cargoList, int currentUserId, OnCargoDeleteListener listener, boolean showDeleteButton) {
        this.cargoList = cargoList;
        this.currentUserId = currentUserId;
        this.deleteListener = listener;
        this.showDeleteButton = showDeleteButton;
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
        holder.title.setText(cargo.getProduct());
        holder.id.setText("#" + cargo.getId());
        holder.description.setText(
                "ТС: " + cargo.getVehicleType() +
                        " | Вес: " + cargo.getWeight() + "т" +
                        " | Объём: " + cargo.getVolume() + "м³");
        holder.from.setText(cargo.getFromLocation());
        holder.to.setText(cargo.getToLocation());
        holder.date.setText(cargo.getDates());
        holder.phone.setText(cargo.getContactPhone());
        holder.price.setText("₽ " + String.format("%.0f", cargo.getPriceByCard()));

        if (showDeleteButton && cargo.getCustomerId() == currentUserId) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDeleteClick(cargo);
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cargoList.size();
    }

    static class CargoViewHolder extends RecyclerView.ViewHolder {
        TextView title, id, description, from, to, date, phone, price;
        Button btnDelete;
        CargoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cargo_title);
            id = itemView.findViewById(R.id.cargo_id);
            description = itemView.findViewById(R.id.cargo_description);
            from = itemView.findViewById(R.id.cargo_from);
            to = itemView.findViewById(R.id.cargo_to);
            date = itemView.findViewById(R.id.cargo_date);
            phone = itemView.findViewById(R.id.cargo_phone);
            price = itemView.findViewById(R.id.cargo_price);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void setCargoList(List<Cargo> newCargoList) {
        this.cargoList = newCargoList;
        notifyDataSetChanged();
    }
}
