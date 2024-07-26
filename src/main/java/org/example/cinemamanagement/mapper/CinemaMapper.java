package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.CinemaDTO;
import org.example.cinemamanagement.model.Cinema;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class CinemaMapper {
    public static CinemaDTO toDTO(Cinema cinema) {
        TypeMap<Cinema, CinemaDTO> typeMap = new ModelMapper().createTypeMap(Cinema.class, CinemaDTO.class);
        CinemaDTO cinemaDTO = typeMap.map(cinema);
        cinemaDTO.setProviderId(cinema.getCinemaProvider().getId());

        return cinemaDTO;
    }
}
