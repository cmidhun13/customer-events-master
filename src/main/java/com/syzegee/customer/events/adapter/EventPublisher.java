package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.service.MessageProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sagar
 */
@Slf4j
@Data
@Component
public class EventPublisher {

    @Autowired
    private MessageProducer messageProducer;


    public void send(String message,String topic) {
        try {
            log.info("Initiate to send in EventPublisher " + " - message: " + message + "  topic: " + topic);
            messageProducer.send(topic, message);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}