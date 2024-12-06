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

/**
 * Adapter class for managing and displaying topping options in a RecyclerView.
 * Supports item selection with optional toggling of selection behavior.
 *
 * @author Yousef Naam, Lukas Chang
 */
public class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ToppingViewHolder> {

    /**
     * Context for inflating views.
     */
    private final Context context;

    /**
     * List of toppings to display.
     */
    private final List<Topping> toppings;

    /**
     * Determines if item selection is allowed.
     */
    private final boolean isSelectable;

    /**
     * Currently selected topping.
     */
    private Topping selectedTopping;

    /**
     * Flag to enable or disable selection behavior.
     */
    private boolean isSelectionEnabled = true;

    /**
     * Listener for handling topping selection events.
     */
    private OnToppingClickListener listener;

    /**
     * Constructor for the ToppingsAdapter.
     *
     * @param context      the context used for inflating views
     * @param toppings     the list of toppings to display
     * @param isSelectable flag to indicate if selection is enabled
     */
    public ToppingsAdapter(Context context, List<Topping> toppings, boolean isSelectable) {
        this.context = context;
        this.toppings = toppings;
        this.isSelectable = isSelectable;
    }

    /**
     * Gets the currently selected topping.
     *
     * @return the selected Topping object, or null if none is selected
     */
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

        // Highlight the selected item
        boolean isSelected = topping.equals(selectedTopping);
        holder.itemView.setAlpha(isSelectionEnabled ? (isSelected ? 0.7f : 1.0f) : 0.5f);

        // Handle item clicks for selection
        if (isSelectionEnabled) {
            holder.itemView.setOnClickListener(v -> {
                selectedTopping = topping; // Update selected topping
                notifyDataSetChanged(); // Refresh UI to reflect changes
                if (listener != null) {
                    listener.onToppingSelected(topping); // Notify listener
                }
            });
        } else {
            holder.itemView.setOnClickListener(null); // Disable click events
        }
    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    /**
     * Enables selection functionality.
     */
    public void enableSelection() {
        isSelectionEnabled = true;
        notifyDataSetChanged();
    }

    /**
     * Disables selection functionality.
     */
    public void disableSelection() {
        isSelectionEnabled = false;
        notifyDataSetChanged();
    }

    /**
     * Sets a listener to handle topping selection events.
     *
     * @param listener the listener for topping selection
     */
    public void setOnToppingClickListener(OnToppingClickListener listener) {
        this.listener = listener;
    }

    /**
     * Listener interface for topping selection events.
     */
    public interface OnToppingClickListener {
        /**
         * Callback for when a topping is selected.
         *
         * @param topping the selected topping
         */
        void onToppingSelected(Topping topping);
    }

    /**
     * ViewHolder class for managing individual topping items in the RecyclerView.
     */
    static class ToppingViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView for displaying topping name.
         */
        TextView toppingName;

        /**
         * Constructor for ToppingViewHolder.
         *
         * @param itemView the view representing a single topping item
         */
        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            toppingName = itemView.findViewById(R.id.toppingName);
        }
    }
}
