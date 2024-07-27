package org.example.cinemamanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    private UUID id;
    private String title;

    private String director;

    @JsonProperty("release_date")
    private Timestamp releaseDate;

    private String country;

    @JsonProperty("restrict_age")
    private Integer restrictAge;

    @JsonProperty("picture_url")
    private String pictureUrl;

    @JsonProperty("trailer_url")
    private String trailerUrl;

    private Integer duration;

    private String description;

    private String language;

    private List<String> tags;

    @JsonProperty("provider_id")
     private UUID cinemaProviderId;

    @JsonProperty("manager_id")
    private UUID managerId;
}
