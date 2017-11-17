package com.ride.hailing.prototype.passenger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.ride.hailing.prototype.passenger.commands.RegisterPassenger;
import com.ride.hailing.prototype.passenger.commands.RequestRide;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpoint;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpointFactory;

public class Passengers extends AbstractActor {

    private ActorRef drivers;
    private PassengerEndpointFactory passengerEndpointFactory;

    public Passengers(ActorRef drivers, PassengerEndpointFactory passengerEndpointFactory) {
        this.drivers = drivers;
        this.passengerEndpointFactory = passengerEndpointFactory;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterPassenger.class, login -> {
                    String passengerName = login.name();
                    PassengerEndpoint passengerEndpoint = passengerEndpointFactory.forPath("/passenger/" + passengerName);
                    context().actorOf(Passenger.props(passengerName, drivers, passengerEndpoint), passengerName);
                })
                .match(RequestRide.class, request ->
                        getContext().findChild(request.passengerName()).ifPresent(passenger -> passenger.tell(request, self()))
                )
                .build();
    }

    public static Props props(ActorRef drivers, PassengerEndpointFactory factory) {
        return Props.create(Passengers.class, () -> new Passengers(drivers, factory));
    }
}
