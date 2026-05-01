package com.example.cinefast;

public class Booking {
    private String bookingId;
    private String movieName;
    private int seats;
    private double totalPrice;
    private String date;
    private String time;
    private long timestamp;

    public Booking() {
    }

    public String getBookingId() { return bookingId; }
    public String getMovieName() { return movieName; }
    public int getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public long getTimestamp() { return timestamp; }
}
