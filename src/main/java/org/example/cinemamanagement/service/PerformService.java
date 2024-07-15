package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.PerformDTO;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PerformService {
    public PageResponse<List<PerformDTO>> getAllPerforms(
            PageSpecificationPerform<Perform> pageSpecification,
            CursorBasedPageable cursorBasedPageable);

    PerformDTO getPerformById(UUID id);
    PerformDTO addPerform(AddPerformRequest addPerformRequest);

    PerformDTO updatePerform(UUID id, Map<String, Object> payload);

    void deletePerform(UUID id);

}