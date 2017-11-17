package com.ride.hailing.prototype.passenger.endpoint;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class PassengerEndpointFactory {

    private SimpMessagingTemplate messagingTemplate;

    public PassengerEndpointFactory(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public PassengerEndpoint forPath(String path) {
        return new PassengerEndpoint(path, messagingTemplate);
    }
}
