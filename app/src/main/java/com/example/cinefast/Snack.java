package com.example.cinefast;

import android.os.Parcel;
import android.os.Parcelable;

public class Snack implements Parcelable {
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

    protected Snack(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        imageRes = in.readInt();
        quantity = in.readInt();
        bgColor = in.readInt();
    }

    public static final Creator<Snack> CREATOR = new Creator<Snack>() {
        @Override
        public Snack createFromParcel(Parcel in) {
            return new Snack(in);
        }

        @Override
        public Snack[] newArray(int size) {
            return new Snack[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(imageRes);
        dest.writeInt(quantity);
        dest.writeInt(bgColor);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageRes() { return imageRes; }
    public int getQuantity() { return quantity; }
    public int getBgColor() { return bgColor; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
