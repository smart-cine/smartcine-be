package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;

import java.util.List;
import java.util.UUID;

public interface CinemaLayoutService {
    public List<CinemaLayoutDTO> getAllCinemaLayout();

    public CinemaLayoutDTO getCinemaLayout(UUID id);

    public CinemaLayoutDTO addCinemaLayout(AddCinemaLayoutRequest addCinemaLayoutRequest);

    public CinemaLayoutDTO updateCinemaLayout(UUID idLayout, CinemaLayoutDTO cinemaLayoutDTO);

    public void deleteCinemaLayout(UUID id);

}
