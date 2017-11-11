package com.ride.hailing.prototype.driver.commands;

public class Hail {

    private String passengerName;

    public Hail(String passengerName) {
        this.passengerName = passengerName;
    }

    public String passengerName() {
        return passengerName;
    }
}
