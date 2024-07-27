package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.CinemaDTO;
import org.example.cinemamanagement.model.Cinema;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.stream.Collectors;

public class CinemaMapper {
    public static CinemaDTO toDTO(Cinema cinema) {
        TypeMap<Cinema, CinemaDTO> typeMap = new ModelMapper().createTypeMap(Cinema.class, CinemaDTO.class);
        CinemaDTO cinemaDTO = typeMap.map(cinema);
        cinemaDTO.setProviderId(cinema.getCinemaProvider().getId());
        cinemaDTO.setManagerIds(cinema.getManagerAccounts().stream().map(managerAccount -> managerAccount.getId()).collect(Collectors.toList()));

        return cinemaDTO;
    }
}
