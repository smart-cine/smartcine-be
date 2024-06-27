package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.FilmDTO;
import org.example.cinemamanagement.mapper.FilmMapper;
import org.example.cinemamanagement.model.Film;
import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.payload.request.AddFilmRequest;
import org.example.cinemamanagement.repository.FilmRepository;
import org.example.cinemamanagement.repository.TagRepository;
import org.example.cinemamanagement.service.FilmService;
import org.example.cinemamanagement.utils.ConvertJsonNameToTypeName;
import org.example.cinemamanagement.utils.CursorBasedPageable;
import org.example.cinemamanagement.utils.PageResponse;
import org.example.cinemamanagement.utils.PageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    TagRepository tagRepository;
    FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(TagRepository tagRepository, FilmRepository filmRepository) {
        this.tagRepository = tagRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public FilmDTO getFilmById(UUID id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));
        return FilmMapper.toDTO(film);
    }

    @Override
    public FilmDTO addFilm(AddFilmRequest addFilmRequest) {
        Boolean checkExistence = filmRepository.existsFilmByTitle(addFilmRequest.getTitle());
        if (checkExistence) {
            throw new RuntimeException("Film already exists");
        }

        List<Tag> tags = addFilmRequest.getTags().stream().map(tag -> {
                    Optional<Tag> currTag = tagRepository.findTagByName(tag.getName());
                    if (currTag.isEmpty()) {
                        return tagRepository.save(Tag.builder().name(tag.getName()).build());
                    }
                    return currTag.get();
                }
        ).toList();

        Film tempFilm = filmRepository.save(Film.builder()
                .title(addFilmRequest.getTitle())
                .director(addFilmRequest.getDirector())
                .country(addFilmRequest.getCountry())
                .releaseDate(addFilmRequest.getReleaseDate())
                .restrictAge(addFilmRequest.getRestrictAge())
                .pictureUrl(addFilmRequest.getPictureUrl())
                .trailerUrl(addFilmRequest.getTrailerUrl())
                .duration(addFilmRequest.getDuration())
                .tags(tags)
                .description(addFilmRequest.getDescription())
                .language(addFilmRequest.getLanguage())
                .build());

        return FilmMapper.toDTO(tempFilm);
    }

    @Override
    public FilmDTO updateFilm(UUID id, Map<String, Object> updatedFields) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        for (Map.Entry<String, Object> entry : updatedFields.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            try {
                Field field = Film.class.getDeclaredField(
                        ConvertJsonNameToTypeName.convert(key)
                );
                field.setAccessible(true);
                field.set(film, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field " + key);
            }
        }
        Film updatedFilm = filmRepository.save(film);
        return FilmMapper.toDTO(updatedFilm);

    }

    @Override
    public void deleteFilm(UUID id) {
        filmRepository.deleteById(id);
    }


    @Override
    public PageResponse<List<FilmDTO>> page(
            PageSpecification<Film> pageSpecification,
            CursorBasedPageable cursorBasedPageable) {

        var filmSlide = filmRepository.findAll(pageSpecification,
                Pageable.ofSize(cursorBasedPageable.getSize()));

        if (!filmSlide.hasContent()) return new PageResponse<>(false, null, null);
        Map<String, String> pagingMap = new HashMap<>();

        List<Film> films = filmSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(films.get(0).getTitle(), hasPreviousPage(films.get(0))));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(films.get(films.size() - 1).getTitle(), filmSlide.hasNext()));
        pagingMap.put("size", String.valueOf(cursorBasedPageable.getSize()));
        pagingMap.put("total", String.valueOf(filmSlide.getTotalElements()));

        return new PageResponse<>(true, films.stream()
                .map(FilmMapper::toDTO).collect(Collectors.toList()),
                pagingMap);
    }

    private Boolean hasPreviousPage(Film firstFilm) {
        Optional<Film> tempFilm = filmRepository.theFirstFilmBehindCurrFilm(firstFilm.getTitle());
        return tempFilm.isPresent();
    }

    private String buildSQLQuery(Map<String, String> paramsString) {
        StringBuilder sqlQuery = new StringBuilder("select * from film where ");
        for (Map.Entry<String, String> entry : paramsString.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                sqlQuery.append(key).append(" like %").append(value).append("%");
            }
        }

        return sqlQuery.toString();
    }


    @Override
    public List<FilmDTO> getFilmByParams(Map<String, String> params) {
        return List.of();
    }
}
