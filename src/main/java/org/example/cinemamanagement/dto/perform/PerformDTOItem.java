package org.example.cinemamanagement.dto.perform;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.cinemamanagement.common.TranslateType;
import org.example.cinemamanagement.common.ViewType;
import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.film.FilmDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PerformDTOItem {
    private UUID id;
    @JsonProperty("translate_type")
    @Enumerated(EnumType.STRING)
    private TranslateType translateType;

    @JsonProperty("view_type")
    @Enumerated(EnumType.STRING)
    private ViewType viewType;
    @JsonProperty("cinema_room")
    private CinemaRoomDTO cinemaRoomDTO;
    @JsonProperty("start_time")
    private Timestamp startTime;
    @JsonProperty("end_time")
    private Timestamp endTime;
    @JsonProperty("film")
    private FilmDTO filmDTO;

    private Double price;
}
