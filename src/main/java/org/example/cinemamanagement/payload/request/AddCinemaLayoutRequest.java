package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.cinemamanagement.common.LayoutType;

import java.util.UUID;

@Data
public class AddCinemaLayoutRequest {
    @JsonProperty("provider_id")
    private UUID providerId;
    private Integer rows;
    private Integer columns;
}
