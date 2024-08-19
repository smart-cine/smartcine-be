package org.example.cinemamanagement.dto.cinema;

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
public class CinemaLayoutDTO {
    private UUID id;

    @JsonProperty("manager_id")
    private UUID managerId;
    private Integer rows;
    private Integer columns;
}
