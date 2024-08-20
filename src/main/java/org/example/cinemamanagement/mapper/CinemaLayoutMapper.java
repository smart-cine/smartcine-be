package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutGroupDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutSeatDTOItem;
import org.example.cinemamanagement.model.CinemaLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaLayoutMapper {
    public static CinemaLayoutDTO toDTO(CinemaLayout cinemaLayout) {
        List<List<Integer>> seats = new LinkedList<>();

        cinemaLayout.getCinemaLayoutSeats().stream().forEach(seat -> {
            seats.add(
                    List.of(seat.getX(), seat.getY())
            );
        });

        return CinemaLayoutDTO.builder()
                .id(cinemaLayout.getId())
                .providerId(cinemaLayout.getCinemaProvider().getId())
                .columns(cinemaLayout.getColumns())
                .rows(cinemaLayout.getRows())
                .seats(seats)
                .build();
    }

    public static CinemaLayoutDTOItem toDTOItem(CinemaLayout cinemaLayout) {
        return CinemaLayoutDTOItem.builder()
                .id(cinemaLayout.getId())
                .rows(cinemaLayout.getRows())
                .columns(cinemaLayout.getColumns())
                .groups(cinemaLayout.getCinemaLayoutGroups().stream().map(group -> CinemaLayoutGroupDTOItem.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .color(group.getColor())
                        .build()).collect(Collectors.toList())
                )
                .seats(
                        cinemaLayout.getCinemaLayoutSeats().stream().map(seat -> CinemaLayoutSeatDTOItem.builder()
                                .id(seat.getId())
                                .groupId(seat.getCinemaLayoutGroup().getId())
                                .code(seat.getCode())
                                .x(seat.getX())
                                .y(seat.getY())
                                .build()).collect(Collectors.toList()
                        ))
                .build();
    }
}
