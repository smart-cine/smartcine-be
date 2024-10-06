package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.perform.PerformDTO;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.payload.response.CinemasPerformResponse;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.payload.response.FilmsPerformResponse;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.service.PerformService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PerformController {
    PerformService performService;

    @Autowired
    public PerformController(PerformService performService) {
        this.performService = performService;
    }

    @GetMapping("perform")
    public ResponseEntity<?> getPerforms(CursorBasedPageable cursorBasedPageable, @RequestParam Map<String, Object> params) {
        try {
            System.out.println(params);
            var specification = new PageSpecificationPerform<Perform>("startTime",
                    cursorBasedPageable,
                    params
            );
            return ResponseEntity.ok(performService.getAllPerforms(specification, cursorBasedPageable));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("current-performs")
    public ResponseEntity<?> getPerformsInRangeDay() {
        return null;
    }

    @GetMapping("perform/{id}")
    public ResponseEntity<?> getPerform(@PathVariable UUID id) {
        DataResponse dataResponse = DataResponse.builder()
                .success(true)
                .message("Get perform successfully")
                .data(performService.getPerformById(id))
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("perform/list-film")
    public ResponseEntity<PageResponse<List<FilmsPerformResponse>>> getPerformsByCinema(CursorBasedPageable cursorBasedPageable, @RequestParam Map<String, Object> params) {
        PageSpecificationPerform<Perform> pageSpecification = new PageSpecificationPerform<Perform>("startTime",
                cursorBasedPageable,
                params
        );

        if (!params.containsKey("cinema_id") || !params.containsKey("start_time")) {
            throw new RuntimeException(
                    "Missing required parameters: start_time or cinema_id"
            );
        }

        return ResponseEntity.ok(performService
                .getPerformsByCinema(pageSpecification, cursorBasedPageable)
        );


//        DataResponse dataResponse = DataResponse.builder()
//                .success(true)
//                .message("Get film by cinema and start time successfully")
//                .data(performService.getFilmByCinemaAndStartTime(params))
//                .build();
//        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("perform/list-cinema")
    public ResponseEntity<PageResponse<List<CinemasPerformResponse>>> getPerformsByFilm(CursorBasedPageable cursorBasedPageable, @RequestParam Map<String, Object> params) {
        PageSpecificationPerform<Perform> pageSpecification = new PageSpecificationPerform<Perform>("startTime",
                cursorBasedPageable,
                params
        );

        if ( !params.containsKey("film_id") || !params.containsKey("start_time")) {
            throw new RuntimeException(
                    "Missing required parameters: start_time or cinema_id"
            );
        }

        System.out.println("hehe");

        return ResponseEntity.ok(performService
                .getPerformsByFilm(pageSpecification, cursorBasedPageable)
        );
    }

    @PostMapping("perform")
    public ResponseEntity<?> addPerform(@RequestBody AddPerformRequest addPerformRequest) {
        DataResponse<PerformDTO> dataResponse = DataResponse.<PerformDTO>builder()
                .success(true)
                .data(performService.addPerform(addPerformRequest))
                .message("Add perform successfully")
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @PatchMapping("perform/{id}")
    public ResponseEntity<?> updatePerform(@PathVariable UUID id, @RequestBody Map<String, Object> payload) {
        DataResponse dataResponse = DataResponse.builder()
                .success(true)
                .data(performService.updatePerform(id, payload))
                .message("Update perform successfully")
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("perform/{id}")
    public ResponseEntity<?> deletePerform() {
        DataResponse dataResponse = DataResponse.builder()
                .success(true)
                .message("Delete perform successfully")
                .build();

        return ResponseEntity.ok(dataResponse);
    }
}
