package com.example.pharmacyservice.dto;

import com.example.pharmacyservice.event.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String message;
    private OrderEvent orderEvent;
}
