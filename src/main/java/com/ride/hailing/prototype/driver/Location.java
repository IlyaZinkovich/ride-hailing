package com.ride.hailing.prototype.driver;

public class Location {

    private double lng;
    private double lat;

    public Location(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
