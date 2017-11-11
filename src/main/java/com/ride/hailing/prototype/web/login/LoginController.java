package com.ride.hailing.prototype.web.login;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.dispatcher.commands.RegisterDriver;
import com.ride.hailing.prototype.passenger.Passenger;
import com.ride.hailing.prototype.web.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static akka.actor.ActorRef.noSender;
import static java.lang.String.format;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("dispatcher")
    private ActorRef dispatcher;

    @Autowired
    @Qualifier("actorSystem")
    private ActorSystem system;

    @MessageMapping("/login/driver")
    @SendTo("/topic/greetings")
    public Reply loginDriver(LoginDriver loginDriver) throws Exception {
        dispatcher.tell(new RegisterDriver(loginDriver.getDriverName()), noSender());
        return new Reply(format("Hello, %s!", loginDriver.getDriverName()));
    }

    @MessageMapping("/login/passenger")
    @SendTo("/topic/greetings")
    public Reply loginPassenger(LoginPassenger loginPassenger) throws Exception {
        final String passengerName = loginPassenger.getPassengerName();
        system.actorOf(Passenger.props(passengerName, dispatcher), passengerName);
        return new Reply(format("Hello, %s!", loginPassenger.getPassengerName()));
    }
}
