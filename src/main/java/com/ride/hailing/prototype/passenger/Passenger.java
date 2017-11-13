package com.ride.hailing.prototype.passenger;

import akka.actor.AbstractFSM;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ride.hailing.prototype.dispatcher.commands.ArrangeRide;
import com.ride.hailing.prototype.passenger.commands.RequestRide;
import com.ride.hailing.prototype.passenger.commands.RideAccepted;

import static com.ride.hailing.prototype.passenger.State.Idle;
import static com.ride.hailing.prototype.passenger.State.Ride;
import static com.ride.hailing.prototype.passenger.State.Wait;

public class Passenger extends AbstractFSM<State, Data> {

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

    {
        startWith(Idle, new PassengerInformation());

        when(Idle, matchEvent(RequestRide.class, PassengerInformation.class,
                (request, info) -> {
                    dispatcher.tell(new ArrangeRide(name), self());
                    return goTo(Wait).using(new RideInformation());
                })
        );

        when(Wait, matchEvent(RideAccepted.class, RideInformation.class,
                (rideAccepted, info) -> {
                    log.info("Passenger `{}` rides with driver `{}`", name, rideAccepted.driverName());
                    return goTo(Ride).using(info);
                })
        );

        when(Ride, matchEvent(RideAccepted.class, RideInformation.class,
                (rideAccepted, rideInformation) -> stay()));
        when(Ride, matchEvent(RequestRide.class, RideInformation.class,
                (request, data) -> stay()));
    }
}

enum State {
    Idle, Wait, Ride
}

interface Data {

}

class PassengerInformation implements Data {

}

class RideInformation implements Data {

}
