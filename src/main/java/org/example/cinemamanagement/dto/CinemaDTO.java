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
public class CinemaDTO {
    UUID id;
    String name;
    String address;

    @JsonProperty("provider_id")
    UUID providerId;
}
