package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddPaymentRequest {
    @JsonProperty("perform_id")
    private UUID performId;

    private Double amount;

    @JsonProperty("pick_seat_ids")
    private List<UUID> pickSeatIds;
}
