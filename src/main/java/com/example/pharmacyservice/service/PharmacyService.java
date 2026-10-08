package com.example.pharmacyservice.service;

import com.example.pharmacyservice.dto.OrderRequest;
import com.example.pharmacyservice.dto.OrderResponse;

public interface PharmacyService {
    OrderResponse checkout(OrderRequest orderRequest);
}
