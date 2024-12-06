package com.example.pizzeria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pizzeria.R;
import com.example.pizzeria.models.Pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for managing the display of pizza items in a RecyclerView.
 * Handles item selection and dynamically updates the data in the RecyclerView.
 *
 * @author Yousef Naam, Lukas Chang
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    /**
     * Context for inflating views.
     */
    private final Context context;

    /**
     * List of pizzas to display.
     */
    private final List<Pizza> pizzas;

    /**
     * Position of the currently selected item.
     */
    private int selectedPosition = RecyclerView.NO_POSITION;

    /**
     * Constructor for the PizzaAdapter.
     *
     * @param context the context used for inflating views
     * @param pizzas the initial list of pizzas to display
     */
    public PizzaAdapter(Context context, List<Pizza> pizzas) {
        this.context = context;
        this.pizzas = (pizzas != null) ? pizzas : new ArrayList<>();
    }

    /**
     * Gets the currently selected pizza.
     *
     * @return the selected Pizza object, or null if none is selected
     */
    public Pizza getSelectedPizza() {
        if (selectedPosition >= 0 && selectedPosition < pizzas.size()) {
            return pizzas.get(selectedPosition);
        }
        return null;
    }

    /**
     * Updates the list of pizzas and refreshes the RecyclerView.
     *
     * @param newPizzas the updated list of pizzas
     */
    public void updatePizzas(List<Pizza> newPizzas) {
        pizzas.clear();
        if (newPizzas != null) {
            pizzas.addAll(newPizzas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        holder.pizzaDetails.setText(pizza.toString());

        // Highlight the selected item
        holder.itemView.setSelected(position == selectedPosition);

        // Handle item selection
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    /**
     * ViewHolder class for managing individual pizza items in the RecyclerView.
     */
    static class PizzaViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView displaying pizza details.
         */
        TextView pizzaDetails;

        /**
         * Constructor for PizzaViewHolder.
         *
         * @param itemView the view representing a single pizza item
         */
        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaDetails = itemView.findViewById(R.id.pizzaDetails);
        }
    }
}
