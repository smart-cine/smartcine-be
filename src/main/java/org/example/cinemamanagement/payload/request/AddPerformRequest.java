package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.TranslateType;
import org.example.cinemamanagement.common.ViewType;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPerformRequest {
    @JsonProperty("film_id")
    private UUID filmId;

    @JsonProperty("manager_id")
    private UUID managerId;

    @JsonProperty("view_type")
    @Enumerated(EnumType.STRING)
    private ViewType viewType;
    @JsonProperty("translate_type")
    @Enumerated(EnumType.STRING)
    private TranslateType translateType;
    @JsonProperty("cinema_room_id")
    private UUID cinemaRoomId;
    @JsonProperty("start_time")
    private Timestamp startTime;
    @JsonProperty("end_time")
    private Timestamp endTime;
    private Double price;
}
