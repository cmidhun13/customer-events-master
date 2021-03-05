package com.syzegee.customer.events.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



/**
 * @author Sagar
 */
@Service
@Slf4j
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        log.info("Topic: "+topic);
        log.info("sending data='{}'", message);
        kafkaTemplate.send(topic, message);
        }
}
