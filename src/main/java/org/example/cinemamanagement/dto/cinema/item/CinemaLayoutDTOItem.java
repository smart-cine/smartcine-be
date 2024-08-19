package org.example.cinemamanagement.dto.cinema.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CinemaLayoutDTOItem {
    private UUID id;
    @JsonProperty("provider_id")
    private UUID providerId;
    private Integer rows;
    private Integer columns;
    private List<CinemaLayoutGroupDTOItem> groups;
    private List<CinemaLayoutSeatDTOItem> seats;
}
