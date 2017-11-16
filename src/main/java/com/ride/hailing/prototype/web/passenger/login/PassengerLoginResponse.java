package com.ride.hailing.prototype.web.passenger.login;

public class PassengerLoginResponse {

    private String passengerId;

    public PassengerLoginResponse() {
    }

    public PassengerLoginResponse(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }
}
