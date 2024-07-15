package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.pagination.PageSpecificationTag;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.service.TagService;
import org.example.cinemamanagement.service.impl.TagServiceImpl;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTags(CursorBasedPageable cursorBasedPageable,
                                        @RequestParam(required = false, name = "film-id") UUID filmId) {
        var specification = new PageSpecificationTag<Tag>("name", cursorBasedPageable, filmId == null ? Map.of() : Map.of("filmId", filmId));
        return ResponseEntity.ok(tagService.getAllTags(cursorBasedPageable, specification));
    }

    @PostMapping
    public ResponseEntity<Tag> createTag() {
        return null;
    }
}
