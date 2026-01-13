package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewBookingsController implements Initializable {
    @FXML
    private VBox bookingsContainer;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBookings();
    }
    
    private void loadBookings() {
        List<BookingItem> bookings = dbHelper.getAllBookings();
        bookingsContainer.getChildren().clear();
        
        if (bookings.isEmpty()) {
            Label noBookingsLabel = new Label("No bookings found");
            noBookingsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            bookingsContainer.getChildren().add(noBookingsLabel);
        } else {
            Label countLabel = new Label("Total Bookings: " + bookings.size());
            countLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
            bookingsContainer.getChildren().add(countLabel);
            
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
        
        Label passengerLabel = new Label("Passenger: " + booking.getPassengerName());
        passengerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label flightLabel = new Label("Flight: " + booking.getFlightNumber());
        flightLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label routeLabel = new Label("Route: " + booking.getRoute());
        routeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label dateLabel = new Label("Date: " + booking.getDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label statusLabel = new Label("Status: " + booking.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #5DE1C0;");
        
        card.getChildren().addAll(bookingIdLabel, passengerLabel, flightLabel, routeLabel, dateLabel, statusLabel);
        return card;
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/admin-home-view.fxml"));
        Parent root = fxmlLoader.load();
        AdminHomeController controller = fxmlLoader.getController();
        controller.setUsername("admin");
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard - Flight Booking");
    }
}
