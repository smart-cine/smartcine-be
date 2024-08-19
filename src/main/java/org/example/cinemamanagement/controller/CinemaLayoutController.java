package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.CinemaLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/layout")
public class CinemaLayoutController {


    CinemaLayoutService cinemaLayoutService;

    @Autowired
    public CinemaLayoutController(CinemaLayoutService cinemaLayoutService) {
        this.cinemaLayoutService = cinemaLayoutService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLayout() {
        DataResponse dataResponse = DataResponse
                .builder()
                .data(cinemaLayoutService.getAllCinemaLayout())
                .message("Get all layout successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<CinemaLayoutDTO>> getLayout(@PathVariable UUID id) {

        DataResponse<CinemaLayoutDTO> dataResponse = DataResponse
                .<CinemaLayoutDTO>builder()
                .data(cinemaLayoutService.getCinemaLayout(id))
                .message("Get layout successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<?> addLayout(@RequestBody AddCinemaLayoutRequest cinemaLayoutRequest) {

        DataResponse dataResponse = DataResponse.builder()
                .message("Add layout successfully")
                .data(cinemaLayoutService.addCinemaLayout(cinemaLayoutRequest))
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLayout(@PathVariable UUID id, @RequestBody CinemaLayoutDTO cinemaLayoutDTO) {
        cinemaLayoutService.updateCinemaLayout(id, cinemaLayoutDTO);

        DataResponse dataResponse = DataResponse.builder()
                .message("Update layout successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLayout(@PathVariable UUID id) {
        cinemaLayoutService.deleteCinemaLayout(id);

        DataResponse dataResponse = DataResponse
                .builder()
                .success(true)
                .message("Delete layout successfully")
                .build();
        return ResponseEntity.ok(dataResponse);
    }

}
