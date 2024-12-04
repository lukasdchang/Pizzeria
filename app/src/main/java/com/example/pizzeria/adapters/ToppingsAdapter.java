package com.example.pizzeria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.models.Topping;

import java.util.List;

public class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ToppingViewHolder> {

    private final Context context;
    private final List<Topping> toppings;
    private final boolean isSelectable; // Add this field to handle the third parameter
    private Topping selectedTopping;

    // Updated constructor
    public ToppingsAdapter(Context context, List<Topping> toppings, boolean isSelectable) {
        this.context = context;
        this.toppings = toppings;
        this.isSelectable = isSelectable; // Assign the boolean parameter
    }

    public Topping getSelectedTopping() {
        return selectedTopping;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topping, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings.get(position);
        holder.toppingName.setText(topping.name());

        // Handle selection logic based on isSelectable
        if (isSelectable) {
            holder.itemView.setOnClickListener(v -> selectedTopping = topping);
        } else {
            holder.itemView.setOnClickListener(null); // Disable selection if not selectable
        }
    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingName;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            toppingName = itemView.findViewById(R.id.toppingName);
        }
    }

    public void enableSelection() {
        // Add logic to enable selection, if necessary
    }

    public void disableSelection() {
        // Add logic to disable selection, if necessary
    }
}