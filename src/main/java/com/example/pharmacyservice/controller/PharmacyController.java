package com.example.pharmacyservice.controller;

import com.example.pharmacyservice.dto.OrderRequest;
import com.example.pharmacyservice.dto.OrderResponse;
import com.example.pharmacyservice.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestBody OrderRequest orderRequest) {
        OrderResponse response = pharmacyService.checkout(orderRequest);
        return ResponseEntity.ok(response);
    }
}
