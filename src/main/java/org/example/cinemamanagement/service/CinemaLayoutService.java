package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutDTOItem;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CinemaLayoutService {
    public PageResponse<List<CinemaLayoutDTO>> getAllCinemaLayout(Specification<CinemaLayout> specification, CursorBasedPageable cursorBasedPageable);

    public CinemaLayoutDTOItem getCinemaLayout(UUID id);

    public CinemaLayoutDTO addCinemaLayout(AddCinemaLayoutRequest addCinemaLayoutRequest);

    public CinemaLayoutDTO updateCinemaLayout(UUID idLayout, CinemaLayoutDTO cinemaLayoutDTO);

    public void deleteCinemaLayout(UUID id);

    public CinemaLayoutDTOItem cloneCinemaLayout(UUID idLayout);

}
