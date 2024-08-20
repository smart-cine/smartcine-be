package org.example.cinemamanagement.dto.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaLayoutDTO {
    private UUID id;
    private Integer rows;
    private Integer columns;
    @JsonProperty("provider_id")
    private UUID providerId;

    private List<List<Integer>> seats;
}
