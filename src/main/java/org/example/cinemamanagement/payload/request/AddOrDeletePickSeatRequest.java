package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddOrDeletePickSeatRequest {
    @JsonProperty("pick_seat_id")
    private UUID id;
}
