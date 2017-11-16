package com.ride.hailing.prototype.driver.commands;

import com.ride.hailing.prototype.driver.Location;

public class ChangeLocation {

    private Location location;

    public ChangeLocation(Location location) {
        this.location = location;
    }

    public Location location() {
        return location;
    }
}
