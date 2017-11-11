package com.ride.hailing.prototype.passenger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ride.hailing.prototype.passenger.commands.RideAccepted;
import com.ride.hailing.prototype.dispatcher.commands.ArrangeRide;

public class Passenger extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String name;

    private ActorRef dispatcher;

    public Passenger(String name, ActorRef dispatcher) {
        this.name = name;
        this.dispatcher = dispatcher;
    }

    public static Props props(String name, ActorRef dispatcher) {
        return Props.create(Passenger.class, () -> new Passenger(name, dispatcher));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(com.ride.hailing.prototype.passenger.commands.RequestRide.class, (request) -> {
                    dispatcher.tell(new ArrangeRide(name), self());
                })
                .match(RideAccepted.class, (rideAccepted) -> {
                    log.info("Passenger `{}` rides with driver `{}`", name, rideAccepted.driverName());
                })
                .build();
    }
}
