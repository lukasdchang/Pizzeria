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
    private boolean isSelectionEnabled = true;


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

        // Highlight selected item (optional)
        boolean isSelected = topping.equals(selectedTopping);
        holder.itemView.setAlpha(isSelectionEnabled ? (isSelected ? 0.7f : 1.0f) : 0.5f);

        // Click listener for selection
        if (isSelectionEnabled) {
            holder.itemView.setOnClickListener(v -> {
                selectedTopping = topping; // Update selected topping
                notifyDataSetChanged(); // Refresh UI to show selection
            });
        } else {
            holder.itemView.setOnClickListener(null); // Disable click events
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
        isSelectionEnabled = true;
        notifyDataSetChanged();
    }

    public void disableSelection() {
        isSelectionEnabled = false;
        notifyDataSetChanged();
    }

    public interface OnToppingClickListener {
        void onToppingSelected(Topping topping);
    }

    private OnToppingClickListener listener;

    public void setOnToppingClickListener(OnToppingClickListener listener) {
        this.listener = listener;
    }
}