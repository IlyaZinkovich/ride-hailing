package com.ride.hailing.prototype;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.dispatcher.Dispatcher;
import com.ride.hailing.prototype.dispatcher.commands.RegisterDriver;
import com.ride.hailing.prototype.dispatcher.commands.RegisterPassenger;

import static akka.actor.ActorRef.noSender;

public class App {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("ride-hailing");
        final ActorRef rideDispatcher = system.actorOf(Dispatcher.props(), "ride-dispatcher");
        rideDispatcher.tell(new RegisterDriver("Vova"), noSender());
        rideDispatcher.tell(new RegisterPassenger("Vasya"), noSender());
    }
}
