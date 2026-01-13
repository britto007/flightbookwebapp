package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDashboardController {
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private VBox bookFlightCard;
    
    @FXML
    private VBox myBookingsCard;
    
    @FXML
    private VBox deleteBookingCard;
    
    private String username;
    
    public void setUsername(String username) {
        this.username = username;
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
    }
    
    @FXML
    public void initialize() {
        if (bookFlightCard != null) {
            bookFlightCard.setOnMouseClicked(e -> {
                try {
                    onBookFlightClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (myBookingsCard != null) {
            myBookingsCard.setOnMouseClicked(e -> {
                try {
                    onMyBookingsClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (deleteBookingCard != null) {
            deleteBookingCard.setOnMouseClicked(e -> {
                try {
                    onDeleteBookingClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/login-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Login - Flight Booking");
    }
    
    @FXML
    protected void onBookFlightClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/user-home-view.fxml"));
        Parent root = fxmlLoader.load();
        UserHomeController controller = fxmlLoader.getController();
        controller.setUsername(username);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Book Flight - Flight Booking");
    }
    
    @FXML
    protected void onMyBookingsClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/my-bookings-view.fxml"));
        Parent root = fxmlLoader.load();
        MyBookingsController controller = fxmlLoader.getController();
        controller.setUsername(username);
        controller.setDeleteMode(false);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("My Bookings - Flight Booking");
    }
    
    @FXML
    protected void onDeleteBookingClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/my-bookings-view.fxml"));
        Parent root = fxmlLoader.load();
        MyBookingsController controller = fxmlLoader.getController();
        controller.setUsername(username);
        controller.setDeleteMode(true);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Delete Booking - Flight Booking");
    }
}
