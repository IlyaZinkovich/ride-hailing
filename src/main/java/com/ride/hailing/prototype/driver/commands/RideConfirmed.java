package com.ride.hailing.prototype.driver.commands;

public class RideConfirmed {

    private String passengerName;

    public RideConfirmed(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
