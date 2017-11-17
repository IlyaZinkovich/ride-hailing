package com.ride.hailing.prototype.web.passenger.login;

import akka.actor.ActorRef;
import com.ride.hailing.prototype.passenger.commands.LoginPassenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerLoginController {

    private ActorRef passengers;

    @Autowired
    public PassengerLoginController(ActorRef passengers) {
        this.passengers = passengers;
    }

    @PostMapping(path = "/login/passenger")
    public PassengerLoginResponse loginPassenger(@RequestBody Credentials credentials) {
        String passengerName = credentials.getEmail().split("@")[0];
        passengers.tell(new LoginPassenger(passengerName), ActorRef.noSender());
        return new PassengerLoginResponse(passengerName);
    }
}
