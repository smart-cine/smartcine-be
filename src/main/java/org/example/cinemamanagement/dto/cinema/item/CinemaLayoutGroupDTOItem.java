package org.example.cinemamanagement.dto.cinema.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CinemaLayoutGroupDTOItem {
    private UUID id;
    private String name;
    private Integer color;
}
