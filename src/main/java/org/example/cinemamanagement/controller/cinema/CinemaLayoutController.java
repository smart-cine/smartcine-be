package org.example.cinemamanagement.controller.cinema;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutDTOItem;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.pagination.PageSpecificationCinemaLayout;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;
import org.example.cinemamanagement.payload.request.CloneLayoutCinemaRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.CinemaLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cinema-layout")
public class CinemaLayoutController {

    CinemaLayoutService cinemaLayoutService;

    @Autowired
    public CinemaLayoutController(CinemaLayoutService cinemaLayoutService) {
        this.cinemaLayoutService = cinemaLayoutService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLayout(CursorBasedPageable cursorBasedPageable, @RequestParam(name = "provider_id", required = false) UUID providerId) {
        try {
            Map<String, Object> filters = new HashMap<>();
            if (providerId != null)
                filters.put("providerId", providerId);

            var specification = new PageSpecificationCinemaLayout<CinemaLayout>(
                    "id",
                    cursorBasedPageable,
                    filters
            );

            return ResponseEntity.ok(cinemaLayoutService.getAllCinemaLayout(specification, cursorBasedPageable));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<CinemaLayoutDTOItem>> getLayout(@PathVariable UUID id) {

        DataResponse<CinemaLayoutDTOItem> dataResponse = DataResponse
                .<CinemaLayoutDTOItem>builder()
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

    @PostMapping("/clone")
    public ResponseEntity<?> cloneLayout(@RequestBody CloneLayoutCinemaRequest cloneLayoutCinemaRequest) {
        DataResponse dataResponse = DataResponse.builder()
                .message("Clone layout successfully")
                .data(cinemaLayoutService.cloneCinemaLayout(cloneLayoutCinemaRequest.getLayoutId()))
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
