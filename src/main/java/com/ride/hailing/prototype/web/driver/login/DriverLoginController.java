package com.ride.hailing.prototype.web.driver.login;

import akka.actor.ActorRef;
import com.ride.hailing.prototype.driver.commands.RegisterDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverLoginController {

    @Autowired
    private ActorRef drivers;

    @PostMapping(path = "/login/driver")
    public DriverLoginResponse loginDriver(@RequestBody Credentials credentials) {
        String driverName = credentials.getEmail().split("@")[0];
        drivers.tell(new RegisterDriver(driverName), ActorRef.noSender());
        return new DriverLoginResponse(driverName);
    }
}
