package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.cinemamanagement.dto.TagDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AddFilmRequest {
    private String title;
    private String director;
    @JsonProperty("release_date")
    private Timestamp releaseDate;
    private String country;
    @JsonProperty("restrict_age")
    private Integer restrictAge;
    private List<String> tags;
    @JsonProperty("picture_url")
    private String pictureUrl;
    @JsonProperty("trailer_url")
    private String trailerUrl;
    private Integer duration;
    private String description;
    private String language;
}
