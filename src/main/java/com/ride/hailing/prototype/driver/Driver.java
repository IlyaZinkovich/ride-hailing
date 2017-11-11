package com.ride.hailing.prototype.driver;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ride.hailing.prototype.driver.commands.Hail;
import com.ride.hailing.prototype.passenger.commands.RideAccepted;

public class Driver extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String name;

    public Driver(String name) {
        this.name = name;
    }

    public static Props props(String name) {
        return Props.create(Driver.class, () -> new Driver(name));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Hail.class, (hail) -> {
                    sender().tell(new RideAccepted(name), self());
                    log.info("Driver `{}` accepted a ride with passenger `{}`", name, hail.passengerName());
                })
                .build();
    }
}
