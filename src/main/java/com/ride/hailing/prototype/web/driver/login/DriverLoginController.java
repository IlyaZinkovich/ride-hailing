package com.ride.hailing.prototype.web.driver.login;

import akka.actor.ActorSystem;
import com.ride.hailing.prototype.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverLoginController {

    @Autowired
    private ActorSystem actorSystem;

    @PostMapping(path = "/login/driver")
    public DriverLoginResponse loginDriver(@RequestBody Credentials credentials) {
        String driverName = credentials.getEmail().split("@")[0];
        actorSystem.actorOf(Driver.props(driverName), driverName);
        return new DriverLoginResponse(driverName);
    }
}
