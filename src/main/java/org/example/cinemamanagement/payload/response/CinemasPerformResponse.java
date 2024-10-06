package org.example.cinemamanagement.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.example.cinemamanagement.dto.perform.PerformDTO;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class CinemasPerformResponse {
    @JsonProperty("cinema_id")
    private UUID cinemaId;
    private List<PerformDTO> performs;
}
