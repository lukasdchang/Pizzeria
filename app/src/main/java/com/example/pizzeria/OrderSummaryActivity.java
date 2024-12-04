package com.example.pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import main.java.Order;
import main.java.models.Pizza;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller class for managing and displaying order summaries.
 * Handles displaying order details, cancelling orders, and exporting
 * orders to a text file. Manages a list of orders and provides an
 * interface for user interactions related to order management.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class OrderSummaryActivity {

    // FXML injected UI elements
    @FXML
    private ComboBox<Integer> orderNumberDropdown; // Dropdown for selecting orders
    @FXML
    private ListView<Pizza> orderDetailsListView;  // List view for displaying order details
    @FXML
    private TextField orderTotalLabel;             // Text field to display the order total
    @FXML
    private Button cancelOrderButton;
    @FXML
    private Button exportOrdersButton;

    private ObservableList<Order> orders;          // List of all orders
    private ObservableList<Integer> orderNumbers;  // List of order numbers for the dropdown

    /**
     * Initializes the controller and sets up the order dropdown, order list,
     * and necessary listeners for user interactions.
     */
    public void initialize() {
        orders = FXCollections.observableArrayList(); // List to store orders
        orderNumbers = FXCollections.observableArrayList(); // List to store order numbers

        // Bind order numbers to the ComboBox
        orderNumberDropdown.setItems(orderNumbers);

        // Listener for order number selection
        orderNumberDropdown.setOnAction(e -> handleOrderSelection());
    }

    /**
     * Handles the selection of an order from the dropdown menu.
     * Displays the order details and updates the order total.
     */
    @FXML
    private void handleOrderSelection() {
        Integer selectedOrderNumber = orderNumberDropdown.getValue();
        if (selectedOrderNumber != null) {
            Order selectedOrder = findOrderByNumber(selectedOrderNumber);
            if (selectedOrder != null) {
                orderDetailsListView.setItems(FXCollections.observableArrayList(selectedOrder.getPizzas()));
                double totalWithTax = selectedOrder.calculateTotalWithTax();
                orderTotalLabel.setText(String.format("$%.2f", totalWithTax));
            }
        }
    }

    /**
     * Handles the cancellation of an order. Removes the selected order
     * from the list of orders and updates the UI.
     */
    @FXML
    private void handleCancelOrder() {
        Integer selectedOrderNumber = orderNumberDropdown.getValue();
        if (selectedOrderNumber != null) {
            Order orderToRemove = findOrderByNumber(selectedOrderNumber);
            if (orderToRemove != null) {
                orders.remove(orderToRemove);
                orderNumbers.remove(selectedOrderNumber);

                // Clear the details view and reset total
                orderDetailsListView.getItems().clear();
                orderTotalLabel.setText("$0.00");

                // Clear selection in the ComboBox
                orderNumberDropdown.getSelectionModel().clearSelection();
            }
        } else {
            showAlert("No Order Selected", "Please select an order to cancel.");
        }
    }

    /**
     * Handles exporting all orders to a text file.
     * Prompts the user to select a file location and writes order details to the file.
     */
    @FXML
    private void handleExportOrders() {
        if (orders.isEmpty()) {
            showAlert("No Orders Available", "There are no orders to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Orders");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (Order order : orders) {
                    writer.write("Order Number: " + order.getOrderNumber() + "\n");
                    writer.write("Pizzas:\n");
                    for (Pizza pizza : order.getPizzas()) {
                        writer.write(pizza.toString() + "\n");
                    }
                    writer.write(String.format("Order Total (Tax Included): $%.2f\n", order.calculateTotalWithTax()));
                    writer.write("\n");
                }
                showAlert("Export Successful", "Orders have been successfully exported.");
            } catch (IOException e) {
                showAlert("Export Failed", "An error occurred while exporting the orders.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds an order by its unique order number.
     *
     * @param orderNumber the order number to search for
     * @return the matching Order object, or null if not found
     */
    private Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Adds a new order to the summary. This method can be called by
     * other controllers to add an order.
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
            orderNumbers.add(order.getOrderNumber()); // Update ComboBox with new order number
        }
    }

    /**
     * Refreshes the order summary view, ensuring that the ComboBox
     * items are up-to-date.
     */
    public void refreshOrderSummary() {
        orderNumberDropdown.setItems(orderNumbers); // Already bound, no need to recreate list
    }
}