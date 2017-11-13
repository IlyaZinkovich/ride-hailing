package com.ride.hailing.prototype.driver;

import akka.actor.AbstractFSM;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ride.hailing.prototype.driver.commands.Hail;
import com.ride.hailing.prototype.driver.commands.RideConfirmed;
import com.ride.hailing.prototype.driver.commands.RideDeclined;
import com.ride.hailing.prototype.driver.fsm.Data;
import com.ride.hailing.prototype.driver.fsm.DriverInformation;
import com.ride.hailing.prototype.driver.fsm.RideInformation;
import com.ride.hailing.prototype.driver.fsm.State;
import com.ride.hailing.prototype.passenger.commands.RideAccepted;

import static com.ride.hailing.prototype.driver.fsm.State.*;

public class Driver extends AbstractFSM<State, Data> {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String name;

    {
        startWith(Idle, new DriverInformation());

        when(Idle, matchEvent(Hail.class, DriverInformation.class,
                (hail, info) -> {
                    log.info("Driver `{}` waits for confirmation from passenger `{}`",
                            name, hail.passengerName());
                    sender().tell(new RideAccepted(name), self());
                    return goTo(Wait).using(new RideInformation());
                })
        );

        when(Wait, matchEvent(RideConfirmed.class, RideInformation.class,
                (confirmation, info) -> {
                    log.info("Driver `{}` rides with passenger `{}`",
                            name, confirmation.passengerName());
                    return goTo(Ride).using(new RideInformation());
                })
        );

        when(Wait, matchEvent(RideDeclined.class, RideInformation.class,
                (decline, info) -> {
                    log.info("Ride is declined for driver `{}`", name);
                    return goTo(Idle).using(new DriverInformation());
                })
        );

        when(Ride, matchAnyEvent((message, data) -> stay()));
    }

    public Driver(String name) {
        this.name = name;
    }

    public static Props props(String name) {
        return Props.create(Driver.class, () -> new Driver(name));
    }
}

