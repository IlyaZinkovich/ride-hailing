package com.ride.hailing.prototype.driver.commands;

public class ArrangeRide {

    private String passengerName;

    public ArrangeRide(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
