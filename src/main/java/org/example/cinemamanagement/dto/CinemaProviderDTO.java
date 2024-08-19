package org.example.cinemamanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
public class CinemaProviderDTO {
    private UUID id;

    private String name;

    @JsonProperty("logo_url")

    private String logoUrl;

    @JsonProperty("background_url")
    private String backgroundUrl;
}
