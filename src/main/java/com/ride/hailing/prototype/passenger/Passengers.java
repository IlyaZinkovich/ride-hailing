package com.ride.hailing.prototype.passenger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.ride.hailing.prototype.passenger.commands.LoginPassenger;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpoint;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpointFactory;

public class Passengers extends AbstractActor {

    private ActorRef dispatcher;
    private PassengerEndpointFactory passengerEndpointFactory;

    public Passengers(ActorRef dispatcher, PassengerEndpointFactory passengerEndpointFactory) {
        this.dispatcher = dispatcher;
        this.passengerEndpointFactory = passengerEndpointFactory;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(LoginPassenger.class, login -> {
                    String passengerName = login.name();
                    PassengerEndpoint passengerEndpoint = passengerEndpointFactory.forPath("/passenger/" + passengerName);
                    context().actorOf(Passenger.props(passengerName, dispatcher, passengerEndpoint));
                })
                .build();
    }

    public static Props props(ActorRef dispatcher, PassengerEndpointFactory factory) {
        return Props.create(Passengers.class, () -> new Passengers(dispatcher, factory));
    }
}
