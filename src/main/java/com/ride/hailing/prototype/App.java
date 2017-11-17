package com.ride.hailing.prototype;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.driver.Drivers;
import com.ride.hailing.prototype.passenger.Passengers;
import com.ride.hailing.prototype.passenger.endpoint.PassengerEndpointFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("ride-hailing");
    }

    @Bean
    public ActorRef drivers(ActorSystem system) {
        return system.actorOf(Drivers.props(), "drivers");
    }

    @Bean
    public ActorRef passengers(ActorSystem system, ActorRef drivers, SimpMessagingTemplate messagingTemplate) {
        return system.actorOf(Passengers.props(drivers, new PassengerEndpointFactory(messagingTemplate)), "passengers");
    }
}
