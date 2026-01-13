package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyBookingsController implements Initializable {
    @FXML
    private VBox bookingsContainer;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Button backButton;
    
    private String username;
    private boolean deleteMode = false;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    public void setUsername(String username) {
        this.username = username;
        // Load bookings after username is set
        if (username != null && !username.isEmpty()) {
            loadBookings();
        }
    }
    
    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        if (titleLabel != null) {
            titleLabel.setText(deleteMode ? "Delete Booking" : "My Bookings");
        }
        // Reload bookings when mode changes
        if (username != null && !username.isEmpty()) {
            loadBookings();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Don't load here - wait for username to be set
    }
    
    private void loadBookings() {
        List<BookingItem> bookings = dbHelper.getBookingsByPassengerName(username);
        bookingsContainer.getChildren().clear();
        
        if (bookings.isEmpty()) {
            Label noBookingsLabel = new Label("No bookings found");
            noBookingsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            bookingsContainer.getChildren().add(noBookingsLabel);
        } else {
            for (BookingItem booking : bookings) {
                VBox bookingCard = createBookingCard(booking);
                bookingsContainer.getChildren().add(bookingCard);
            }
        }
    }
    
    private VBox createBookingCard(BookingItem booking) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #34495E; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0, 0, 2); -fx-border-color: #3498DB; -fx-border-width: 2; -fx-border-radius: 12;");
        
        Label bookingIdLabel = new Label("Booking ID: " + booking.getBookingId());
        bookingIdLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label flightLabel = new Label("Flight: " + booking.getFlightNumber());
        flightLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label routeLabel = new Label("Route: " + booking.getRoute());
        routeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label dateLabel = new Label("Date: " + booking.getDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label statusLabel = new Label("Status: " + booking.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #5DE1C0;");
        
        if (deleteMode) {
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 0 12 0; -fx-background-radius: 5; -fx-cursor: hand;");
            deleteButton.setMaxWidth(Double.MAX_VALUE);
            deleteButton.setOnAction(e -> onDeleteBooking(booking));
            card.getChildren().addAll(bookingIdLabel, flightLabel, routeLabel, dateLabel, statusLabel, deleteButton);
        } else {
            card.getChildren().addAll(bookingIdLabel, flightLabel, routeLabel, dateLabel, statusLabel);
        }
        
        return card;
    }
    
    private void onDeleteBooking(BookingItem booking) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Booking " + booking.getBookingId() + "?");
        confirmAlert.setContentText("This action cannot be undone.");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dbHelper.deleteBooking(booking.getKey());
                loadBookings(); // Refresh the list
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Booking Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Booking has been deleted successfully.");
                successAlert.showAndWait();
            }
        });
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/user-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        UserDashboardController controller = fxmlLoader.getController();
        controller.setUsername(username);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("User Dashboard - Flight Booking");
    }
}
