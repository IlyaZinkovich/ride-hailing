package com.ride.hailing.prototype.dispatcher.commands;

public class RegisterDriver {

    private String driverName;

    public RegisterDriver(String driverName) {
        this.driverName = driverName;
    }

    public String driverName() {
        return driverName;
    }
}
