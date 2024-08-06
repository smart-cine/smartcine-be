package org.example.cinemamanagement.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cinemamanagement.dto.PaymentDTO;
import org.example.cinemamanagement.payload.request.AddPaymentRequest;
import org.example.cinemamanagement.payload.request.OrderRequestDTO;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

public interface PaymentService {
    public PaymentDTO getPayment(UUID id);

    public String addPayment(AddPaymentRequest req);

    public Map<String, Object> createOrder(HttpServletRequest request, OrderRequestDTO orderRequestDTO)
            throws UnsupportedEncodingException;

    public void handlePayment(Map<String, String> params);
}
