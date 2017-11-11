package com.ride.hailing.prototype;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ride.hailing.prototype.dispatcher.Dispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
    public ActorRef dispatcher(ActorSystem system) {
        return system.actorOf(Dispatcher.props(), "ride-dispatcher");
    }
}
