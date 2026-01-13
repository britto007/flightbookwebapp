package com.example.flightbook;

public class ScheduleItem {
    private String flightNumber;
    private String from;
    private String to;
    private String departureTime;
    private String arrivalTime;
    private String frequency;

    public ScheduleItem() {
    }

    public ScheduleItem(String flightNumber, String from, String to, 
                        String departureTime, String arrivalTime, String frequency) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.frequency = frequency;
    }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
}
