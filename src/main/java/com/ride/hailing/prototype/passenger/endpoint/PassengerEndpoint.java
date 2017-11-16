package com.ride.hailing.prototype.passenger.endpoint;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class PassengerEndpoint {

    private String path;
    private SimpMessagingTemplate messagingTemplate;

    public PassengerEndpoint(String path, SimpMessagingTemplate messagingTemplate) {
        this.path = path;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToPassenger(String message) {
        messagingTemplate.convertAndSend(path, message);
    }
}
