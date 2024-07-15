package org.example.cinemamanagement.service;


import org.example.cinemamanagement.dto.CinemaDTO;
import org.example.cinemamanagement.dto.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.CinemaManagerDTO;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.payload.request.AddCinemaRequest;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CinemaService {
    public List<CinemaDTO> getAllCinema();

    public CinemaDTO getCinema(UUID id);

    public CinemaDTO addCinema(AddCinemaRequest addCinemaRequest);

    public void deleteCinema(UUID id);

    public CinemaDTO updateCinema(UUID id, Map<String, Object> payload);

    // public CinemaLayoutDTO addCinemaLayoutIntoCinema(UUID idCinema, CinemaLayoutDTO cinemaLayoutDTO);

    public List<CinemaLayoutDTO> getCinemaLayoutByCinemaId(UUID id);

    //  public List<CinemaRoomDTO> getAllCinemaRoomByCinemaId(UUID id);

    public CinemaManagerDTO deleteCinemaManagerOutOfCinema(String emailUser, UUID idCinema);


    public PageResponse<List<CinemaDTO>> page(PageSpecification<Cinema> pageSpecification, CursorBasedPageable cursorBasedPageable);

}
