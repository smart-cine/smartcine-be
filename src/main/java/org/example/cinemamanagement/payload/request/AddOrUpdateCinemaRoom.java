package org.example.cinemamanagement.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.RoomType;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrUpdateCinemaRoom {
    private String name;
    @JsonProperty("cinema_id")
    private UUID cinemaId;
    @JsonProperty("cinema_layout_id")
    private UUID cinemaLayoutId;
}
