package org.example.cinemamanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cinemamanagement.payload.request.OrderRequestDTO;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable UUID id) {
        DataResponse dataResponse = DataResponse.builder()
                .message("Get payment successfully")
                .data(paymentService.getPayment(id))
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<?> createPayment(
            HttpServletRequest request,
            @RequestBody OrderRequestDTO req) throws Exception {
        DataResponse dataResponse = DataResponse.builder()
                .message("Add payment successfully").success(true)
                .data(paymentService.createOrder(request, req))
                .build();
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("/ipn")
    public ResponseEntity<?> handlePayment(@RequestParam Map<String, String> params) {
        paymentService.handlePayment(params);
        return ResponseEntity.ok(DataResponse.builder()
                .message("Payment handled successfully")
                .success(true)
                .build());
    }
}