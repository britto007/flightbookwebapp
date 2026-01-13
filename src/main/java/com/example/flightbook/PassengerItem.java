package com.example.flightbook;

public class PassengerItem {
    private String name;
    private String email;
    private String phone;
    private String passport;
    private String flightNumber;
    private String route;
    private String date;

    public PassengerItem() {
    }

    public PassengerItem(String name, String email, String phone, String passport, 
                        String flightNumber, String route, String date) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.flightNumber = flightNumber;
        this.route = route;
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
