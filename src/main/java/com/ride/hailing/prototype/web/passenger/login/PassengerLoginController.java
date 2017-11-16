package com.ride.hailing.prototype.web.passenger.login;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.passenger.Passenger;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerLoginController {

    private ActorSystem actorSystem;

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PassengerLoginController(ActorSystem actorSystem, SimpMessagingTemplate messagingTemplate) {
        this.actorSystem = actorSystem;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping(path = "/login/passenger")
    public PassengerLoginResponse loginPassenger(@RequestBody Credentials credentials) {
        String passengerName = credentials.getEmail().split("@")[0];
        PassengerEndpoint passengerEndpoint = new PassengerEndpoint("/passenger/" + passengerName, messagingTemplate);
        actorSystem.actorOf(Passenger.props(passengerName, ActorRef.noSender(), passengerEndpoint), passengerName);
        return new PassengerLoginResponse(passengerName);
    }
}
