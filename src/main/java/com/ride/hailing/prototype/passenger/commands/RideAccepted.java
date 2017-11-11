package com.ride.hailing.prototype.passenger.commands;

public class RideAccepted {

    private String driverName;

    public RideAccepted(String driverName) {
        this.driverName = driverName;
    }

    public String driverName() {
        return driverName;
    }
}
