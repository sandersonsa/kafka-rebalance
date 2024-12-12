package xyz.sandersonsa.kafkaproducer.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Async
    public CompletableFuture<String> sendMessages(Integer quantidade) throws InterruptedException {                
        for (int i = 0; i < quantidade; i++) {
            UUID uuid = UUID.randomUUID();
            kafkaTemplate.send("t-rebalance", uuid.toString());
        }
        System.out.println(quantidade + " mensagens enviadas");
        return CompletableFuture.completedFuture("Complete");
    }

}
