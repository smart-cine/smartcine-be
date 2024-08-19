package org.example.cinemamanagement.dto.cinema.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class CinemaLayoutSeatDTOItem {
    private UUID id;
    @JsonProperty("group_id")
    private UUID groupId;
    private String code;
    private Integer x;
    private Integer y;
}
