package com.example.flightbook;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:flightbooking.db";
    private static DatabaseHelper instance;
    
    private DatabaseHelper() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    key TEXT UNIQUE,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT,
                    role TEXT NOT NULL,
                    status TEXT NOT NULL,
                    joinDate TEXT
                )
            """;
            stmt.execute(createUsersTable);
            
            // Create flights table
            String createFlightsTable = """
                CREATE TABLE IF NOT EXISTS flights (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    key TEXT UNIQUE,
                    flightNumber TEXT NOT NULL,
                    from_city TEXT NOT NULL,
                    to_city TEXT NOT NULL,
                    departureDate TEXT NOT NULL,
                    departureTime TEXT NOT NULL,
                    arrivalDate TEXT,
                    arrivalTime TEXT,
                    price TEXT,
                    aircraftType TEXT
                )
            """;
            stmt.execute(createFlightsTable);
            
            // Create bookings table
            String createBookingsTable = """
                CREATE TABLE IF NOT EXISTS bookings (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    key TEXT UNIQUE,
                    bookingId TEXT UNIQUE NOT NULL,
                    passengerName TEXT NOT NULL,
                    flightNumber TEXT NOT NULL,
                    route TEXT NOT NULL,
                    date TEXT NOT NULL,
                    status TEXT NOT NULL,
                    seatClass TEXT,
                    phone TEXT,
                    adults INTEGER DEFAULT 0,
                    children INTEGER DEFAULT 0
                )
            """;
            stmt.execute(createBookingsTable);
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // User operations
    public UserItem getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserItem user = new UserItem();
                user.setKey(rs.getString("key"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setJoinDate(rs.getString("joinDate"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }
    
    public boolean createUser(String username, String password) {
        if (getUserByUsername(username) != null) {
            return false; // User already exists
        }
        
        String key = "user_" + System.currentTimeMillis();
        String email = username.contains("@") ? username : username + "@flightbooking.com";
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.getDefault());
        String joinDate = dateFormat.format(Calendar.getInstance().getTime());
        
        String sql = "INSERT INTO users (key, username, password, email, role, status, joinDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, "User");
            pstmt.setString(6, "Active");
            pstmt.setString(7, joinDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    
    public List<UserItem> getAllUsers() {
        List<UserItem> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UserItem user = new UserItem();
                user.setKey(rs.getString("key"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setJoinDate(rs.getString("joinDate"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }
    
    // Flight operations
    public void addFlight(FlightItem flight) {
        String key = "flight_" + System.currentTimeMillis();
        String sql = "INSERT INTO flights (key, flightNumber, from_city, to_city, departureDate, departureTime, arrivalDate, arrivalTime, price, aircraftType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.setString(2, flight.getFlightNumber());
            pstmt.setString(3, flight.getFrom());
            pstmt.setString(4, flight.getTo());
            pstmt.setString(5, flight.getDepartureDate());
            pstmt.setString(6, flight.getDepartureTime());
            pstmt.setString(7, flight.getArrivalDate());
            pstmt.setString(8, flight.getArrivalTime());
            pstmt.setString(9, flight.getPrice());
            pstmt.setString(10, flight.getAircraftType());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Flight added successfully: " + flight.getFlightNumber() + 
                                 " | From: '" + flight.getFrom() + "' | To: '" + flight.getTo() + "'");
            }
        } catch (SQLException e) {
            System.err.println("Error adding flight: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<FlightItem> getAllFlights() {
        List<FlightItem> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FlightItem flight = new FlightItem();
                flight.setKey(rs.getString("key"));
                flight.setFlightNumber(rs.getString("flightNumber"));
                flight.setFrom(rs.getString("from_city"));
                flight.setTo(rs.getString("to_city"));
                flight.setDepartureDate(rs.getString("departureDate"));
                flight.setDepartureTime(rs.getString("departureTime"));
                flight.setArrivalDate(rs.getString("arrivalDate"));
                flight.setArrivalTime(rs.getString("arrivalTime"));
                flight.setPrice(rs.getString("price"));
                flight.setAircraftType(rs.getString("aircraftType"));
                flights.add(flight);
            }
            System.out.println("Retrieved " + flights.size() + " flights from database");
        } catch (SQLException e) {
            System.err.println("Error getting all flights: " + e.getMessage());
            e.printStackTrace();
        }
        return flights;
    }
    
    public void deleteFlight(String key) {
        String sql = "DELETE FROM flights WHERE key = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting flight: " + e.getMessage());
        }
    }
    
    // Booking operations
    public void addBooking(BookingItem booking) {
        String key = "booking_" + System.currentTimeMillis();
        booking.setKey(key); // Set the key in the booking object
        String sql = "INSERT INTO bookings (key, bookingId, passengerName, flightNumber, route, date, status, seatClass, phone, adults, children) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.setString(2, booking.getBookingId());
            pstmt.setString(3, booking.getPassengerName());
            pstmt.setString(4, booking.getFlightNumber());
            pstmt.setString(5, booking.getRoute());
            pstmt.setString(6, booking.getDate());
            pstmt.setString(7, booking.getStatus());
            pstmt.setString(8, booking.getSeatClass());
            pstmt.setString(9, booking.getPhone());
            pstmt.setInt(10, booking.getAdults());
            pstmt.setInt(11, booking.getChildren());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking saved successfully: " + booking.getBookingId() + " for " + booking.getPassengerName());
            }
        } catch (SQLException e) {
            System.err.println("Error adding booking: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<BookingItem> getAllBookings() {
        List<BookingItem> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BookingItem booking = new BookingItem();
                booking.setKey(rs.getString("key"));
                booking.setBookingId(rs.getString("bookingId"));
                booking.setPassengerName(rs.getString("passengerName"));
                booking.setFlightNumber(rs.getString("flightNumber"));
                booking.setRoute(rs.getString("route"));
                booking.setDate(rs.getString("date"));
                booking.setStatus(rs.getString("status"));
                booking.setSeatClass(rs.getString("seatClass"));
                booking.setPhone(rs.getString("phone"));
                booking.setAdults(rs.getInt("adults"));
                booking.setChildren(rs.getInt("children"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    public List<BookingItem> getBookingsByPassengerName(String passengerName) {
        List<BookingItem> bookings = new ArrayList<>();
        if (passengerName == null || passengerName.trim().isEmpty()) {
            return bookings;
        }
        String sql = "SELECT * FROM bookings WHERE passengerName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, passengerName.trim());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BookingItem booking = new BookingItem();
                booking.setKey(rs.getString("key"));
                booking.setBookingId(rs.getString("bookingId"));
                booking.setPassengerName(rs.getString("passengerName"));
                booking.setFlightNumber(rs.getString("flightNumber"));
                booking.setRoute(rs.getString("route"));
                booking.setDate(rs.getString("date"));
                booking.setStatus(rs.getString("status"));
                booking.setSeatClass(rs.getString("seatClass"));
                booking.setPhone(rs.getString("phone"));
                booking.setAdults(rs.getInt("adults"));
                booking.setChildren(rs.getInt("children"));
                bookings.add(booking);
            }
            System.out.println("Found " + bookings.size() + " bookings for passenger: " + passengerName);
        } catch (SQLException e) {
            System.err.println("Error getting bookings by passenger: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
    
    public void deleteBooking(String key) {
        String sql = "DELETE FROM bookings WHERE key = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
        }
    }
}
