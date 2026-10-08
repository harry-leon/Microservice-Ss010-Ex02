package com.example.pharmacyservice.service.impl;

import com.example.pharmacyservice.dto.OrderRequest;
import com.example.pharmacyservice.dto.OrderResponse;
import com.example.pharmacyservice.event.OrderEvent;
import com.example.pharmacyservice.producer.OrderEventProducer;
import com.example.pharmacyservice.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderResponse checkout(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(orderId)
                .medicineId(orderRequest.getMedicineId())
                .quantity(orderRequest.getQuantity())
                .timestamp(now)
                .build();

        log.info("Processing checkout for medicineId: {}, quantity: {}, generated orderId: {}",
                orderRequest.getMedicineId(), orderRequest.getQuantity(), orderId);

        orderEventProducer.sendOrderEvent(orderEvent);

        return OrderResponse.builder()
                .message("Thanh toán thành công! Sự kiện đơn hàng thuốc đã được gửi vào Kafka topic 'medicine-stock-events'.")
                .orderEvent(orderEvent)
                .build();
    }
}
