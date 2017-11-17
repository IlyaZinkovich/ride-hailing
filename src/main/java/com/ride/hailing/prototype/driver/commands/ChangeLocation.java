package com.ride.hailing.prototype.driver.commands;

import com.ride.hailing.prototype.driver.Location;

public class ChangeLocation {

    private String driverName;
    private Location location;

    public ChangeLocation(String driverName, Location location) {
        this.driverName = driverName;
        this.location = location;
    }

    public Location location() {
        return location;
    }

    public String driverName() {
        return driverName;
    }
}
