package org.example.cinemamanagement.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Setter
@Getter
public class PickSeatResponse {
    private UUID id;
    @JsonProperty("account_id")
    private UUID accountID;

    @JsonProperty("layout_seat_id")
    private UUID seatID;
    private String code;

    @JsonProperty("create_at")
    private Timestamp createAt;

}
