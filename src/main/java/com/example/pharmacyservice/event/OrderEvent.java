package com.example.pharmacyservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private String orderId;
    private String medicineId;
    private Integer quantity;
    private LocalDateTime timestamp;
}
