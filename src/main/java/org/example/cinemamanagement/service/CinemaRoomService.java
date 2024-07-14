package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.CinemaRoomDTO;
import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.payload.request.AddOrUpdateCinemaRoom;
import org.example.cinemamanagement.utils.CursorBasedPageable;
import org.example.cinemamanagement.utils.PageResponse;
import org.example.cinemamanagement.utils.PageSpecification;

import java.util.List;
import java.util.UUID;

public interface CinemaRoomService {

    public PageResponse<List<CinemaRoomDTO>> getAllCinemaRooms(CursorBasedPageable cursorBasedPageable, PageSpecification<CinemaRoom> specification);

    public CinemaRoomDTO getCinemaRoomById(UUID id);

    public CinemaRoomDTO addCinemaRoom(AddOrUpdateCinemaRoom addCinemaRoomRequest);

    public void updateCinemaRoom(UUID id, AddOrUpdateCinemaRoom addOrUpdateCinemaRoom);

    public void deleteCinemaRoom(UUID id);


}
