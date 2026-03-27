package com.example.cinefast;

public class Snack {
    private String name;
    private String description;
    private double price;
    private int imageRes;
    private int quantity;
    private int bgColor;

    public Snack(String name, String description, double price, int imageRes, int bgColor) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageRes = imageRes;
        this.bgColor = bgColor;
        this.quantity = 0;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageRes() { return imageRes; }
    public int getQuantity() { return quantity; }
    public int getBgColor() { return bgColor; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
