package com.everis.rabbitmq;

import com.everis.RestApp;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class Runner {

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void run(Integer precision, String token, String ticket, String extension, String ocr) throws Exception {
        System.out.println("Sending message...");
        StringBuilder message = new StringBuilder(precision.toString());
        message.append("|").append(token);
        message.append("|").append(ticket);
        message.append("|").append(extension);
        message.append("|").append(ocr);
        rabbitTemplate.convertAndSend(RestApp.TASK_QUEUE_NAME, message.toString());
    }
}
