package com.project.jemberliburan.Model;

public class Destination {
    private String name;
    private String location;
    private int imageResourceId;
    private float rating;

    public Destination(String name, String location, int imageResourceId, float rating) {
        this.name = name;
        this.location = location;
        this.imageResourceId = imageResourceId;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public float getRating() {
        return rating;
    }
}
