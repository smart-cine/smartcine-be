package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.PerformDTO;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.PerformService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

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
    public ResponseEntity<?> getPerforms(CursorBasedPageable cursorBasedPageable, @RequestParam(required = true, name = "cinema-id") UUID cinemaId) {
        try {
            var specification = new PageSpecificationPerform<Perform>("startTime",
                    cursorBasedPageable,
                    Map.of("cinemaId", cinemaId));
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
