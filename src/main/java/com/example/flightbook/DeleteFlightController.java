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

public class DeleteFlightController implements Initializable {
    @FXML
    private VBox flightsContainer;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFlights();
    }
    
    private void loadFlights() {
        List<FlightItem> flights = dbHelper.getAllFlights();
        flightsContainer.getChildren().clear();
        
        if (flights.isEmpty()) {
            Label noFlightsLabel = new Label("No flights found");
            noFlightsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            flightsContainer.getChildren().add(noFlightsLabel);
        } else {
            for (FlightItem flight : flights) {
                VBox flightCard = createFlightCard(flight);
                flightsContainer.getChildren().add(flightCard);
            }
        }
    }
    
    private VBox createFlightCard(FlightItem flight) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #34495E; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0, 0, 2); -fx-border-color: #E74C3C; -fx-border-width: 2; -fx-border-radius: 12;");
        
        Label flightNumberLabel = new Label("Flight: " + flight.getFlightNumber());
        flightNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label routeLabel = new Label(flight.getFrom() + " → " + flight.getTo());
        routeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label dateLabel = new Label("Date: " + flight.getDepartureDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 0 12 0; -fx-background-radius: 5; -fx-cursor: hand;");
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> onDeleteFlight(flight));
        
        card.getChildren().addAll(flightNumberLabel, routeLabel, dateLabel, deleteButton);
        return card;
    }
    
    private void onDeleteFlight(FlightItem flight) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Flight " + flight.getFlightNumber() + "?");
        confirmAlert.setContentText("This will delete the flight and all associated bookings.\nThis action cannot be undone.");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dbHelper.deleteFlight(flight.getKey());
                loadFlights(); // Refresh the list
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Flight Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Flight and all associated bookings have been deleted successfully.");
                successAlert.showAndWait();
            }
        });
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
