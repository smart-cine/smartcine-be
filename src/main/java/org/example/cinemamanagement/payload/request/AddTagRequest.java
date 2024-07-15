package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class AddTagRequest {
    @JsonProperty("film_id")
    private UUID filmId;
    private List<String> tags;
}
