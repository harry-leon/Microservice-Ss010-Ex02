package com.example.pharmacyservice.producer;

import com.example.pharmacyservice.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.order-events:medicine-stock-events}")
    private String topicName;

    public void sendOrderEvent(OrderEvent orderEvent) {
        String key = orderEvent.getMedicineId();
        log.info("Sending order event to topic='{}' with key='{}': {}", topicName, key, orderEvent);

        CompletableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(topicName, key, orderEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Order event sent successfully! Topic: {}, Partition: {}, Offset: {}, Key: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        key);
            } else {
                log.error("Failed to send order event with key='{}': {}", key, ex.getMessage(), ex);
            }
        });
    }
}
