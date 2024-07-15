package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.PerformService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("perform" )
    public ResponseEntity<?> getPerforms(CursorBasedPageable cursorBasedPageable, @RequestParam(required = false, name = "cinema-id") UUID cinemaId) {
        try{
            var specification = new PageSpecificationPerform<Perform>("startTime",
                    cursorBasedPageable,
                    Map.of("cinemaId", cinemaId));
            return ResponseEntity.ok(performService.getAllPerforms(specification, cursorBasedPageable));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("current-performs")
    public ResponseEntity<?> getPerformsInRangeDay()
    {
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
        DataResponse dataResponse = new DataResponse();
        dataResponse.setMessage("Add perform successfully");
        dataResponse.setData(performService.addPerform(addPerformRequest));
        dataResponse.setSuccess(true);

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
