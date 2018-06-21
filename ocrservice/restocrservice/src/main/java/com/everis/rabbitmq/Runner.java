package com.everis.rabbitmq;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
public class Runner {

    /*@Autowired
    private QueueClient queueClientForSending;

    public void run(Integer precision, String token, String ticket, String extension, String ocr) throws Exception {
        System.out.println("Sending message...");
        StringBuilder message = new StringBuilder(precision.toString());
        message.append("|").append(token);
        message.append("|").append(ticket);
        message.append("|").append(extension);
        message.append("|").append(ocr);

        try {
            sendQueueMessage(message.toString());
        } catch (ServiceBusException e) {
            System.out.println("Error processing messages: "+ e);
        } catch (InterruptedException e) {
            System.out.println("Error processing messages: " + e);
        }
    }

    private void sendQueueMessage(String ms) throws ServiceBusException, InterruptedException {
        System.out.println("Sending message: " + ms);
        final Message message = new Message(
                ms.getBytes(StandardCharsets.UTF_8));
        queueClientForSending.send(message);
    }*/
}
