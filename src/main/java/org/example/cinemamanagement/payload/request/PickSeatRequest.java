package org.example.cinemamanagement.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickSeatRequest {

    @JsonProperty("perform_id")
    private UUID performID;
    @JsonProperty("layout_seat_id")
    private UUID layoutSeatID;


}
