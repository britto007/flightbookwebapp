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

public class AdminHomeController {
    @FXML
    private Label adminNameLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private VBox addFlightCard;
    
    @FXML
    private VBox deleteFlightCard;
    
    @FXML
    private VBox viewBookingsCard;
    
    @FXML
    private VBox manageUsersCard;
    
    private String username;
    
    public void setUsername(String username) {
        this.username = username;
        if (adminNameLabel != null) {
            adminNameLabel.setText(username);
        }
    }
    
    @FXML
    public void initialize() {
        if (addFlightCard != null) {
            addFlightCard.setOnMouseClicked(e -> {
                try {
                    onAddFlightClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (deleteFlightCard != null) {
            deleteFlightCard.setOnMouseClicked(e -> {
                try {
                    onDeleteFlightClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (viewBookingsCard != null) {
            viewBookingsCard.setOnMouseClicked(e -> {
                try {
                    onViewBookingsClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (manageUsersCard != null) {
            manageUsersCard.setOnMouseClicked(e -> {
                try {
                    onManageUsersClick();
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
    protected void onAddFlightClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/add-flight-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Add Flight - Flight Booking");
    }
    
    @FXML
    protected void onDeleteFlightClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/delete-flight-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Delete Flight - Flight Booking");
    }
    
    @FXML
    protected void onViewBookingsClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/view-bookings-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("View Bookings - Flight Booking");
    }
    
    @FXML
    protected void onManageUsersClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/manage-users-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Manage Users - Flight Booking");
    }
}
