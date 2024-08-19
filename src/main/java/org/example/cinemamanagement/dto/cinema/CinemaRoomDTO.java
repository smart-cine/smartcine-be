package org.example.cinemamanagement.dto.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.RoomType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaRoomDTO {
    private UUID id;
    @JsonProperty("cinema_id")
    private UUID cinemaId;
    @JsonProperty("cinema_layout_id")
    private UUID cinemaLayoutId;
    private String name;
}


