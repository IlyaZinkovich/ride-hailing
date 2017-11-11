package com.ride.hailing.prototype.dispatcher;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.Broadcast;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;
import com.ride.hailing.prototype.dispatcher.commands.ArrangeRide;
import com.ride.hailing.prototype.dispatcher.commands.RegisterDriver;
import com.ride.hailing.prototype.driver.Driver;
import com.ride.hailing.prototype.driver.commands.Hail;

public class Dispatcher extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private Router router = new Router(new RoundRobinRoutingLogic());

    public static Props props() {
        return Props.create(Dispatcher.class, Dispatcher::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterDriver.class, registerDriver -> {
                    final String driverName = registerDriver.driverName();
                    final ActorRef registeredDriver = context().actorOf(Driver.props(driverName), driverName);
                    router = router.addRoutee(registeredDriver);
                    log.info("Driver with name `{}` is registered", driverName);
                })
                .match(ArrangeRide.class, (arrangeRide) -> {
                    final String passengerName = arrangeRide.passengerName();
                    log.info("Arranging a ride for `{}`", passengerName);
                    router.route(new Broadcast(new Hail(passengerName)), sender());
                })
                .matchAny(any -> log.error("Unspecified message {}", any))
                .build();
    }
}
