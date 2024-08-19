package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutSeatDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaRoomDTOItem;
import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.payload.request.AddOrUpdateCinemaRoom;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CinemaRoomService {

    public PageResponse<List<CinemaRoomDTO>> getAllCinemaRooms(CursorBasedPageable cursorBasedPageable, PageSpecification<CinemaRoom> specification);

    public CinemaRoomDTOItem getCinemaRoomById(UUID id);

    public CinemaRoomDTO addCinemaRoom(AddOrUpdateCinemaRoom addCinemaRoomRequest);

    public CinemaRoomDTO updateCinemaRoom(UUID id, AddOrUpdateCinemaRoom addOrUpdateCinemaRoom);

    public void deleteCinemaRoom(UUID id);


}
