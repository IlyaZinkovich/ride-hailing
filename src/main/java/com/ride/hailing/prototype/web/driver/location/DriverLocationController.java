package com.ride.hailing.prototype.web.driver.location;

import akka.actor.ActorSystem;
import com.ride.hailing.prototype.driver.Location;
import com.ride.hailing.prototype.driver.commands.ChangeLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static akka.actor.ActorRef.noSender;

@Controller
public class DriverLocationController {

    @Autowired
    private ActorSystem actorSystem;

    @MessageMapping("/drivers/{driverId}/location")
    public void changeLocation(@DestinationVariable String driverId, DriverLocation driverLocation) {
        actorSystem.actorSelection("/user/" + driverId)
                .tell(new ChangeLocation(new Location(driverLocation.getLng(), driverLocation.getLat())), noSender());
    }
}
