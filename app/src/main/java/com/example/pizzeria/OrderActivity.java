package com.example.pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.models.Pizza;
import main.java.Order;

/**
 * Controller class for managing orders in the RU Pizzeria application.
 * Handles adding, removing, and clearing pizzas from an order,
 * as well as calculating and displaying order totals, sales tax, and subtotals.
 *
 * This controller interacts with the OrderSummaryActivity to facilitate order
 * placement and maintains the state of the current order.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class OrderActivity {

    // FXML injected UI elements
    @FXML
    private Label orderNumberLabel;
    @FXML
    private ListView<Pizza> orderListView;
    @FXML
    private TextField subtotalLabel;
    @FXML
    private TextField salesTaxLabel;
    @FXML
    private TextField orderTotalLabel;
    @FXML
    private Button removePizzaButton;
    @FXML
    private Button clearOrderButton;
    @FXML
    private Button placeOrderButton;

    private Order currentOrder; // Current order instance
    private static int orderCounter = 1; // Counter to generate unique order numbers
    private ObservableList<Pizza> orderItems; // Observable list for ListView display
    private OrderSummaryActivity orderSummaryActivity; // Reference to OrderSummaryActivity
    private static boolean isOrderInitialized = false; // Tracks if order has been initialized

    /**
     * No-argument constructor for JavaFX.
     * Initializes the controller without parameters.
     */
    public OrderActivity() {
    }

    /**
     * Creates a new order with a unique order number.
     */
    private void createNewOrder() {
        currentOrder = new Order(); // Constructor sets unique order number
    }

    /**
     * Handles the removal of a selected pizza from the order.
     */
    @FXML
    private void handleRemovePizza() {
        Pizza selectedPizza = orderListView.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            currentOrder.removePizza(selectedPizza);
            orderItems.remove(selectedPizza);
            updateTotals();
        } else {
            showAlert("No pizza selected", "Please select a pizza to remove.");
        }
    }

    /**
     * Handles clearing all pizzas from the current order.
     */
    @FXML
    private void handleClearOrder() {
        currentOrder.clearOrder();
        orderItems.clear();
        updateTotals();
    }

    /**
     * Handles placing the order by transferring it to the OrderSummaryActivity.
     */
    @FXML
    private void handlePlaceOrder() {
        if (!orderItems.isEmpty()) {
            if (orderSummaryActivity != null) {
                orderSummaryActivity.addOrder(currentOrder); // Add current order
                orderSummaryActivity.refreshOrderSummary(); // Refresh order summary view
            } else {
                showAlert("Order Summary Controller Missing", "Cannot place the order. OrderSummaryActivity is not set.");
                return;
            }

            // Clear and create a new order
            currentOrder = new Order();
            orderItems.clear();
            updateOrderNumber();
            updateTotals();
        } else {
            showAlert("Order is empty", "Cannot place an empty order.");
        }
    }

    /**
     * Updates the order number label with the current order number.
     */
    private void updateOrderNumber() {
        orderNumberLabel.setText("Order Number: " + currentOrder.getOrderNumber());
    }

    /**
     * Updates the subtotal, sales tax, and order total labels based on the
     * current order's contents.
     */
    private void updateTotals() {
        double subtotal = currentOrder.calculateTotal();
        double salesTax = subtotal * 0.06625; // New Jersey sales tax
        double total = subtotal + salesTax;

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        salesTaxLabel.setText(String.format("$%.2f", salesTax));
        orderTotalLabel.setText(String.format("$%.2f", total));
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Adds a pizza to the current order and updates the order view.
     *
     * @param pizza the pizza to add to the order
     */
    public void addPizzaToOrder(Pizza pizza) {
        if (pizza != null) {
            currentOrder.addPizza(pizza);
            orderItems.add(pizza); // Updates ObservableList and ListView
            updateTotals();
        }
    }

    /**
     * Sets the reference to the OrderSummaryActivity for communication.
     *
     * @param orderSummaryActivity the OrderSummaryActivity instance
     */
    public void setOrderSummaryController(OrderSummaryActivity orderSummaryActivity) {
        this.orderSummaryActivity = orderSummaryActivity;
    }

    /**
     * Initializes the controller, sets up the order list view, and
     * creates a new order if one has not been initialized.
     * This method is automatically called after the FXML is loaded.
     */
    public void initialize() {
        if (!isOrderInitialized) {
            currentOrder = new Order(); // Create a new order
            isOrderInitialized = true;
        }

        // Initialize observable list for order items
        orderItems = FXCollections.observableArrayList();
        orderListView.setItems(orderItems);

        // Display initial order number and totals
        updateOrderNumber();
        updateTotals();
    }
}
