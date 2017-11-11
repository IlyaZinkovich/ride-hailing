package com.ride.hailing.prototype.web.ride;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.passenger.commands.RequestRide;
import com.ride.hailing.prototype.web.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static akka.actor.ActorRef.noSender;
import static java.lang.String.format;

@Controller
public class RideController {

    @Autowired
    @Qualifier("actorSystem")
    private ActorSystem system;

    @MessageMapping("/ride")
    @SendTo("/topic/greetings")
    public Reply ride(Ride ride) throws Exception {
        final ActorSelection passenger = system.actorSelection(system.child(ride.getPassengerName()));
        passenger.tell(new RequestRide(), noSender());
        return new Reply(format("%s requested a ride!", ride.getPassengerName()));
    }
}
