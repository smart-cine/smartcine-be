package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.cinema.CinemaProviderDTO;
import org.example.cinemamanagement.model.CinemaProvider;

public class CinemaProviderMapper {
    static public CinemaProviderDTO toDTO(CinemaProvider cinemaProvider) {
        return CinemaProviderDTO.builder()
                .backgroundUrl(cinemaProvider.getBackgroundUrl())
                .id(cinemaProvider.getId())
                .name(cinemaProvider.getName())
                .logoUrl(cinemaProvider.getLogoUrl())
                .build();
    }

    static public CinemaProvider toEntity(CinemaProviderDTO cinemaProviderDTO) {
        return CinemaProvider.builder()
                .backgroundUrl(cinemaProviderDTO.getBackgroundUrl())
                .id(cinemaProviderDTO.getId())
                .name(cinemaProviderDTO.getName())
                .logoUrl(cinemaProviderDTO.getLogoUrl())
                .build();
    }
}
