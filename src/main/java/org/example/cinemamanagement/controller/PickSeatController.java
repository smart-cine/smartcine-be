package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.payload.request.DeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.PickSeatRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.payload.response.PickSeatResponse;
import org.example.cinemamanagement.payload.response.SocketResponse;
import org.example.cinemamanagement.service.PickSeatService;
import org.example.cinemamanagement.service.RedisService;
import org.example.cinemamanagement.service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pickseat")
public class PickSeatController {

    PickSeatService pickSeatService;

    @Autowired
    public PickSeatController(PickSeatService pickSeatService) {
        this.pickSeatService = pickSeatService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PickSeatResponse>>> getPickSeats(@RequestParam(name = "perform_id", required = true) UUID performID) {
        try {
            List<PickSeatResponse> pickSeatResponses = pickSeatService.getAllSeatsPickedOfPerform(performID);
            return ResponseEntity.ok(DataResponse.<List<PickSeatResponse>>builder()
                    .data(pickSeatResponses)
                    .message("Get all picked seats successfully")
                    .success(true)
                    .build());

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addPickSeats(@RequestBody PickSeatRequest pickSeatRequest) {

        return ResponseEntity.ok(DataResponse.builder()
                .data(pickSeatService.addPickSeat(pickSeatRequest))
                .message("Add pick seat successfully")
                .success(true)
                .build());
    }

    @DeleteMapping
    public ResponseEntity<?> deletePickSeat(@RequestBody PickSeatRequest deletePickSeatRequest) {
        pickSeatService.deletePickSeat(deletePickSeatRequest);

        return ResponseEntity.ok(DataResponse.builder()
                .data(null)
                .message("Delete pick seat successfully")
                .success(true)
                .build());

    }
}

