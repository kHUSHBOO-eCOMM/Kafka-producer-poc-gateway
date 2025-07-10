package com.kk.kk.gateway.service;

import com.kk.kk.gateway.models.TransactionMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//@Slf4j
public class KafkaProducerService {

    private  final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    KafkaTemplate<UUID, TransactionMessage> kafkaTemplate;

    public void send(String topicName, UUID key, TransactionMessage transactionMessage){

//        CompletableFuture<SendResult<UUID, TransactionMessage>> test = kafkaTemplate.send(topicName, key, transactionMessage);
        var future = kafkaTemplate.send(topicName, key, transactionMessage);
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            }else {
                future.complete(sendResult);
            }
           /* log.info("Transaction Id is :  " + transactionMessage.getTransactionId()
                    +" Transaction status to kafka topic: "  +transactionMessage.getStatus());*/
            logger.info(  "Transaction Id is :  " + transactionMessage.getTransactionId()
                    +" Transaction status to kafka topic: "  +transactionMessage.getStatus());
        });
    }
}
