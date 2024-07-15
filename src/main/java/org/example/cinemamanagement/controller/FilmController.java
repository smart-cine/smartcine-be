package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.FilmDTO;
import org.example.cinemamanagement.model.Film;
import org.example.cinemamanagement.payload.request.AddFilmRequest;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.FilmService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/film")
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<?> getFilms(CursorBasedPageable cursorBasedPageable, @RequestParam(required = false, name = "title") String searchValue) {
        var specification = new PageSpecification<Film>("title", "", searchValue, cursorBasedPageable);
        PageResponse<List<FilmDTO>> filmPage = filmService.page(specification, cursorBasedPageable);
        return ResponseEntity.ok(filmPage);
    }

    @PostMapping
    public ResponseEntity<?> addFilm(@RequestBody AddFilmRequest addFilmRequest) {
        FilmDTO filmDTO = filmService.addFilm(addFilmRequest);
        DataResponse dataResponse = DataResponse.builder()
                .message("Add film successfully")
                .data(filmDTO)
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable UUID id) {
        DataResponse dataReponse = DataResponse.builder()
                .message("Delete film successfully")
                .success(true)
                .build();
        filmService.deleteFilm(id);
        return ResponseEntity.ok(dataReponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable UUID id) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setMessage("Get film by id successfully");
        dataResponse.setData(filmService.getFilmById(id));
        dataResponse.setSuccess(true);

        return ResponseEntity.ok(dataResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable UUID id, @RequestBody Map<String, Object> updatedFields) {
        FilmDTO filmDTO = filmService.updateFilm(id, updatedFields);
        DataResponse dataResponse = DataResponse.builder()
                .message("Update film successfully")
                .data(filmDTO)
                .success(true)
                .build();

        return ResponseEntity.ok(dataResponse);
    }
}
