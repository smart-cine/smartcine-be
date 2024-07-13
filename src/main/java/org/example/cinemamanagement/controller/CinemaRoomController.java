package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.payload.request.AddOrUpdateCinemaRoom;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.CinemaRoomService;
import org.example.cinemamanagement.utils.CursorBasedPageable;
import org.example.cinemamanagement.utils.PageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cinema-room")
public class CinemaRoomController {

    @Autowired
    CinemaRoomService cinemaRoomService;

    @GetMapping
    public ResponseEntity<?> getAllRoom(CursorBasedPageable cursorBasedPageable,
                                        @RequestParam(required = false, name = "name") String searchValue) {
        var specification = new PageSpecification<CinemaRoom>("name", cursorBasedPageable, searchValue);

        return ResponseEntity.ok(
                cinemaRoomService.getAllCinemaRooms(cursorBasedPageable, specification)
        );


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable UUID id) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setMessage("Get cinema room by id successfully");
        dataResponse.setSuccess(true);
        dataResponse.setData(cinemaRoomService.getCinemaRoomById(id));

        return ResponseEntity.ok(dataResponse);
    }


    @PostMapping
    public ResponseEntity<?> addRoom(@RequestBody AddOrUpdateCinemaRoom addOrUpdateCinemaRoom) {

        DataResponse dataResponse = new DataResponse();
        dataResponse.setMessage("Add cinema room successfully");
        dataResponse.setData(cinemaRoomService.addCinemaRoom(addOrUpdateCinemaRoom));
        dataResponse.setSuccess(true);

        return ResponseEntity.ok(dataResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable UUID id, @RequestBody AddOrUpdateCinemaRoom addOrUpdateCinemaRoom) {
        cinemaRoomService.updateCinemaRoom(id, addOrUpdateCinemaRoom);
        return ResponseEntity.ok(
                DataResponse
                        .builder()
                        .message("Room updated")
                        .success(true)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable UUID id) {
        cinemaRoomService.deleteCinemaRoom(id);
        return ResponseEntity.ok(
                DataResponse
                        .builder()
                        .message("Room deleted")
                        .success(true)
                        .data(id)
                        .build()
        );
    }
}
