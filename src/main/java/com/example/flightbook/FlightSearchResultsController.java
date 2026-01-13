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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlightSearchResultsController implements Initializable {
    @FXML
    private VBox resultsContainer;
    
    @FXML
    private Label noResultsLabel;
    
    @FXML
    private Button backButton;
    
    private String searchFrom;
    private String searchTo;
    private String searchDate;
    private String searchClass;
    private String passengerName;
    private int adultCount;
    private int childCount;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    public void setSearchParams(String from, String to, String date, String flightClass, 
                                String passengerName, int adultCount, int childCount) {
        this.searchFrom = from;
        this.searchTo = to;
        this.searchDate = date;
        this.searchClass = flightClass;
        this.passengerName = passengerName;
        this.adultCount = adultCount;
        this.childCount = childCount;
        loadResults();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Will be populated when setSearchParams is called
    }
    
    private void loadResults() {
        List<FlightItem> allFlights = dbHelper.getAllFlights();
        List<FlightItem> matchingFlights = new ArrayList<>();
        
        // Debug: Print total flights in database
        System.out.println("Total flights in database: " + allFlights.size());
        for (FlightItem f : allFlights) {
            System.out.println("Flight: " + f.getFlightNumber() + " | From: '" + f.getFrom() + "' | To: '" + f.getTo() + "'");
        }
        
        // Normalize search terms (trim and lowercase for case-insensitive matching)
        String normalizedSearchFrom = searchFrom != null ? searchFrom.trim().toLowerCase() : "";
        String normalizedSearchTo = searchTo != null ? searchTo.trim().toLowerCase() : "";
        
        System.out.println("Searching for: From='" + normalizedSearchFrom + "' To='" + normalizedSearchTo + "'");
        
        if (normalizedSearchFrom.isEmpty() || normalizedSearchTo.isEmpty()) {
            resultsContainer.getChildren().clear();
            noResultsLabel.setVisible(true);
            noResultsLabel.setText("Please enter both origin and destination cities.");
            return;
        }
        
        for (FlightItem flight : allFlights) {
            if (flight.getFrom() == null || flight.getTo() == null) {
                System.out.println("Skipping flight with null cities: " + flight.getFlightNumber());
                continue;
            }
            
            // Case-insensitive matching: normalize flight cities to lowercase
            String flightFrom = flight.getFrom().trim().toLowerCase();
            String flightTo = flight.getTo().trim().toLowerCase();
            
            // Check if flight cities match search cities (case-insensitive)
            // Use contains for flexible matching - if search term is contained in flight city or vice versa
            boolean fromMatches = flightFrom.contains(normalizedSearchFrom) || 
                                 normalizedSearchFrom.contains(flightFrom);
            boolean toMatches = flightTo.contains(normalizedSearchTo) || 
                              normalizedSearchTo.contains(flightTo);
            
            System.out.println("Checking flight " + flight.getFlightNumber() + 
                             ": From='" + flightFrom + "' (matches: " + fromMatches + 
                             ") To='" + flightTo + "' (matches: " + toMatches + ")");
            
            if (fromMatches && toMatches) {
                System.out.println("MATCH FOUND: " + flight.getFlightNumber());
                matchingFlights.add(flight);
            }
        }
        
        System.out.println("Total matching flights: " + matchingFlights.size());
        
        resultsContainer.getChildren().clear();
        
        if (matchingFlights.isEmpty()) {
            noResultsLabel.setVisible(true);
            noResultsLabel.setText("No flights found matching your search criteria.\n" +
                                 "Searched: " + searchFrom + " → " + searchTo + 
                                 "\n\nTotal flights in database: " + allFlights.size());
        } else {
            noResultsLabel.setVisible(false);
            for (FlightItem flight : matchingFlights) {
                VBox flightCard = createFlightCard(flight);
                resultsContainer.getChildren().add(flightCard);
            }
        }
    }
    
    private VBox createFlightCard(FlightItem flight) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #34495E; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0, 0, 2); -fx-border-color: #3498DB; -fx-border-width: 2; -fx-border-radius: 12;");
        
        Label flightNumberLabel = new Label("Flight: " + flight.getFlightNumber());
        flightNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label routeLabel = new Label(flight.getFrom() + " → " + flight.getTo());
        routeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label dateLabel = new Label("Date: " + flight.getDepartureDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label timeLabel = new Label("Time: " + flight.getDepartureTime());
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label priceLabel = new Label("Price: $" + flight.getPrice());
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5DE1C0;");
        
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 0 12 0; -fx-background-radius: 5; -fx-cursor: hand;");
        bookButton.setMaxWidth(Double.MAX_VALUE);
        bookButton.setOnAction(e -> onBookFlight(flight));
        
        card.getChildren().addAll(flightNumberLabel, routeLabel, dateLabel, timeLabel, priceLabel, bookButton);
        return card;
    }
    
    private void onBookFlight(FlightItem flight) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Booking");
        confirmAlert.setHeaderText("Book Flight " + flight.getFlightNumber() + "?");
        confirmAlert.setContentText("Route: " + flight.getFrom() + " → " + flight.getTo() + "\n" +
                                   "Date: " + flight.getDepartureDate() + "\n" +
                                   "Time: " + flight.getDepartureTime());
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                createBooking(flight);
            }
        });
    }
    
    private void createBooking(FlightItem flight) {
        String bookingId = "BK" + String.format("%03d", (int)(Math.random() * 1000));
        String route = flight.getFrom() + " → " + flight.getTo();
        
        BookingItem booking = new BookingItem();
        booking.setBookingId(bookingId);
        booking.setPassengerName(passengerName != null ? passengerName.trim() : "");
        booking.setFlightNumber(flight.getFlightNumber());
        booking.setRoute(route);
        booking.setDate(flight.getDepartureDate());
        booking.setStatus("Confirmed");
        booking.setSeatClass(searchClass != null ? searchClass : "Economy");
        booking.setPhone(""); // Can be added later
        booking.setAdults(adultCount);
        booking.setChildren(childCount);
        
        // Save booking to database
        dbHelper.addBooking(booking);
        
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Booking Confirmed");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Your booking has been confirmed!\nBooking ID: " + bookingId + "\nPassenger: " + passengerName);
        successAlert.showAndWait();
        
        try {
            navigateToDashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        navigateToDashboard();
    }
    
    private void navigateToDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/user-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        UserDashboardController controller = fxmlLoader.getController();
        controller.setUsername(passengerName);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("User Dashboard - Flight Booking");
    }
}
