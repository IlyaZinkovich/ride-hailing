package com.ride.hailing.prototype.passenger;

import akka.actor.AbstractFSM;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ride.hailing.prototype.dispatcher.commands.ArrangeRide;
import com.ride.hailing.prototype.driver.commands.RideConfirmed;
import com.ride.hailing.prototype.driver.commands.RideDeclined;
import com.ride.hailing.prototype.passenger.commands.RequestRide;
import com.ride.hailing.prototype.passenger.commands.RideAccepted;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpoint;
import com.ride.hailing.prototype.passenger.fsm.Data;
import com.ride.hailing.prototype.passenger.fsm.PassengerInformation;
import com.ride.hailing.prototype.passenger.fsm.RideInformation;
import com.ride.hailing.prototype.passenger.fsm.State;

import static com.ride.hailing.prototype.passenger.fsm.State.*;

public class Passenger extends AbstractFSM<State, Data> {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String name;
    private ActorRef dispatcher;
    private PassengerEndpoint passengerEndpoint;

    public Passenger(String name, ActorRef dispatcher, PassengerEndpoint passengerEndpoint) {
        this.name = name;
        this.dispatcher = dispatcher;
        this.passengerEndpoint = passengerEndpoint;

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
                    sender().tell(new RideConfirmed(name), self());
                    return goTo(Ride).using(info);
                })
        );

        when(Ride, matchEvent(RideAccepted.class, RideInformation.class,
                (rideAccepted, rideInformation) -> {
                    sender().tell(new RideDeclined(), self());
                    return stay();
                }));

        when(Ride, matchEvent(RideAccepted.class, RideInformation.class,
                (rideAccepted, rideInformation) -> stay()));
        when(Ride, matchEvent(RequestRide.class, RideInformation.class,
                (request, data) -> stay()));
    }

    public static Props props(String name, ActorRef dispatcher, PassengerEndpoint passengerEndpoint) {
        return Props.create(Passenger.class, () -> new Passenger(name, dispatcher, passengerEndpoint));
    }
}

