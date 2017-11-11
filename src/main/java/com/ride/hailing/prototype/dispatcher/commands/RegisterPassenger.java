package com.ride.hailing.prototype.dispatcher.commands;

public class RegisterPassenger {

    private String passengerName;

    public RegisterPassenger(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
