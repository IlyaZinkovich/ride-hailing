package com.ride.hailing.prototype.web.ride;

import akka.actor.ActorRef;
import com.ride.hailing.prototype.passenger.commands.RequestRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideController {

    private ActorRef passengers;

    @Autowired
    public RideController(ActorRef passengers) {
        this.passengers = passengers;
    }

    @PostMapping(path = "/rides")
    public void ride(@RequestBody RideRequest rideRequest) {
        passengers.tell(new RequestRide(rideRequest.getPassengerName()), ActorRef.noSender());
    }
}
