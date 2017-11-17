package com.ride.hailing.prototype.passenger.commands;

public class RegisterPassenger {

    private String name;

    public RegisterPassenger(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
