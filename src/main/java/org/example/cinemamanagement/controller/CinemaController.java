package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.CinemaDTO;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.payload.request.AddCinemaRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.CinemaService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;


    @GetMapping
    public ResponseEntity<?> getAllCinema(
            CursorBasedPageable cursorBasedPageable,
            @RequestParam(required = false) String nameCinemaSearching) {
        var specification = new PageSpecification<Cinema>("name", "", nameCinemaSearching, cursorBasedPageable);
        PageResponse<List<CinemaDTO>> cinemaPage = cinemaService.page(specification, cursorBasedPageable);
        return ResponseEntity.ok(cinemaPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCinema(@PathVariable(name = "id") UUID id) {
        DataResponse dataRes = DataResponse.builder()
                .message("Get cinema successfully")
                .data(cinemaService.getCinema(id))
                .success(true)
                .build();

        return ResponseEntity.ok(dataRes);
    }

    @PostMapping
    public ResponseEntity<?> addCinema(@RequestBody AddCinemaRequest addCinemaRequest) {

        DataResponse dataResponse = DataResponse.builder()
                .message("Add cinema successfully")
                .data(cinemaService.addCinema(addCinemaRequest))
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCinema(@PathVariable UUID id, @RequestBody Map<String, Object> payload) {
        DataResponse dataResponse = DataResponse.builder()
                .message("Update cinema successfully")
                .data(cinemaService.updateCinema(id, payload))
                .success(true)
                .build();
        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable(value = "id") UUID id) {
        cinemaService.deleteCinema(id);
        DataResponse dataResponse = DataResponse.builder()
                .message("Cinema deleted successfully")
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
    }

}
