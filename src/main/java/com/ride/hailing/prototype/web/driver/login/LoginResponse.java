package com.ride.hailing.prototype.web.driver.login;

public class LoginResponse {

    private String driverId;

    public LoginResponse() {
    }

    public LoginResponse(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
