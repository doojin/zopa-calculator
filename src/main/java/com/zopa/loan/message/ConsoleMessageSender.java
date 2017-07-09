package com.zopa.loan.message;

import org.springframework.stereotype.Component;

@Component
public class ConsoleMessageSender implements MessageSender {

    @Override
    public void send(String message) {
        System.out.println(message);
    }
}
