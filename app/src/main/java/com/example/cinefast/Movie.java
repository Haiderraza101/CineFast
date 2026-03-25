package com.example.cinefast;

public class Movie {
    private String name;
    private String description;
    private int imageRes;
    private String trailerUrl;
    private boolean isComingSoon;

    public Movie(String name, String description, int imageRes, String trailerUrl, boolean isComingSoon) {
        this.name = name;
        this.description = description;
        this.imageRes = imageRes;
        this.trailerUrl = trailerUrl;
        this.isComingSoon = isComingSoon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public boolean isComingSoon() {
        return isComingSoon;
    }
}
