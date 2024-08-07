package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.CinemaProviderDTO;
import org.example.cinemamanagement.model.CinemaProvider;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.CinemaProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cinema-provider")
public class CinemaProviderController {

    private final CinemaProviderService cinemaProviderService;

    public CinemaProviderController(CinemaProviderService cinemaProviderService) {
        this.cinemaProviderService = cinemaProviderService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<CinemaProviderDTO>>> getAllCinemaProviders() {
        return ResponseEntity.ok(DataResponse.<List<CinemaProviderDTO>>builder()
                .data(cinemaProviderService.getAllCinemaProviders())
                .success(true)
                .message("Success")
                .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<CinemaProviderDTO>> getCinemaProviderById(@PathVariable UUID id) {
        CinemaProviderDTO cinemaProviderDTO = cinemaProviderService.getCinemaProviderById(id);
        if (cinemaProviderDTO != null) {
            return ResponseEntity.ok(DataResponse.<CinemaProviderDTO>builder()
                    .data(cinemaProviderDTO)
                    .success(true)
                    .message("Success")
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<CinemaProviderDTO>> saveCinemaProvider(@RequestBody CinemaProviderDTO cinemaProviderDTO) {
        return ResponseEntity.ok(DataResponse.<CinemaProviderDTO>builder()
                .data(cinemaProviderService.saveCinemaProvider(cinemaProviderDTO))
                .success(true)
                .message("Success")
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> updateCinemaProvider(@PathVariable UUID id, @RequestBody CinemaProviderDTO cinemaProviderDTO )
    {
        cinemaProviderService.updateCinemaProvider(id, cinemaProviderDTO);

        return ResponseEntity.ok(DataResponse.<Void>builder()
                .success(true)
                .message("Success")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deleteCinemaProvider(@PathVariable UUID id) {
        cinemaProviderService.deleteCinemaProvider(id);
        return ResponseEntity.ok(DataResponse.<Void>builder()
                .success(true)
                .message("Success")
                .build());
    }

}