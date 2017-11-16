package com.ride.hailing.prototype.web.driver.login;

public class DriverLoginResponse {

    private String driverId;

    public DriverLoginResponse() {
    }

    public DriverLoginResponse(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
