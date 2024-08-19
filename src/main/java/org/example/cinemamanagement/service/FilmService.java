package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.film.FilmDTO;
import org.example.cinemamanagement.model.Film;
import org.example.cinemamanagement.payload.request.AddFilmRequest;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FilmService {

    public FilmDTO getFilmById(UUID id);

    public FilmDTO addFilm(AddFilmRequest addFilmRequest);

    public FilmDTO updateFilm(UUID id, Map<String, Object> updatedFields);

    public void deleteFilm(UUID id);

    public PageResponse<List<FilmDTO>> page(PageSpecification<Film> pageSpecification,
                                            CursorBasedPageable cursorBasedPageable);

    public List<FilmDTO> getFilmByParams(Map<String, String> params);
}
