package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.cinema.CinemaProviderDTO;

import java.util.List;
import java.util.UUID;

public interface CinemaProviderService {

    List<CinemaProviderDTO> getAllCinemaProviders();

    CinemaProviderDTO getCinemaProviderById(UUID id);

    CinemaProviderDTO saveCinemaProvider(CinemaProviderDTO cinemaProviderDTO);

    void deleteCinemaProvider(UUID id);

    void updateCinemaProvider(UUID id, CinemaProviderDTO cinemaProviderDTO);
}