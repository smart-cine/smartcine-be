package org.example.cinemamanagement.dto.cinema.item;

import ch.qos.logback.core.Layout;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CinemaRoomDTOItem {
    private UUID id;
    @JsonProperty("cinema_id")
    private UUID cinemaId;
    private String name;
    private  CinemaLayoutDTOItem layout;
}
