package com.ride.hailing.prototype.dispatcher.commands;

public class ArrangeRide {

    private String passengerName;

    public ArrangeRide(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
