package com.ride.hailing.prototype.passenger.commands;

public class LoginPassenger {

    private String name;

    public LoginPassenger(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
