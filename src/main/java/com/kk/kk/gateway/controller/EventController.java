package com.kk.kk.gateway.controller;

import com.kk.kk.gateway.models.TransactionMessage;
import com.kk.kk.gateway.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/event")
    ResponseEntity<String> event(@RequestBody TransactionMessage transactionMessage){
        UUID uuid =UUID.randomUUID();
        kafkaProducerService.send("transaction-topic" ,uuid,transactionMessage);
        return ResponseEntity.ok("sent");
    }


}
