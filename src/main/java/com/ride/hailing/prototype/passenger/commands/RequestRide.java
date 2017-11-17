package com.ride.hailing.prototype.passenger.commands;

public class RequestRide {

    private String passengerName;

    public RequestRide(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
