package org.example.cinemamanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaRoomDTO {
    private UUID id;
    @JsonProperty("cinema")
    private CinemaDTO cinemaDTO;
    @JsonProperty("cinema_layout")
    private CinemaLayoutDTO cinemaLayoutDTO;
    private String name;
}


