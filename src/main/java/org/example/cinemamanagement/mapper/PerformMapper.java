package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.film.FilmDTO;
import org.example.cinemamanagement.dto.perform.PerformDTO;
import org.example.cinemamanagement.dto.perform.PerformDTOItem;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.payload.response.CinemasPerformResponse;
import org.example.cinemamanagement.payload.response.FilmsPerformResponse;

import java.util.*;
import java.util.stream.Collectors;

public class PerformMapper {
    public static PerformDTO toDTO(Perform perform) {
        return PerformDTO.builder()
                .viewType(perform.getViewType())
                .translateType(perform.getTranslateType())
                .cinemaRoomId(perform.getCinemaRoom().getId())
                .filmId(perform.getFilm().getId())
                .startTime(perform.getStartTime())
                .endTime(perform.getEndTime())
                .id(perform.getId())
                .price(perform.getPrice())
                .build();
    }

    public static List<FilmsPerformResponse> listFilmsResponse(List<Perform> performs) {
        HashMap<UUID, LinkedList<PerformDTO>> map = new HashMap<>();

        performs.forEach(perform ->
                {
                    if (map.containsKey(perform.getFilm().getId()))
                        map.get(perform.getFilm().getId()).add(toDTO(perform));
                    else {
                        LinkedList<PerformDTO> list = new LinkedList<>();
                        list.add(toDTO(perform));
                        map.put(perform.getFilm().getId(), list);
                    }

                }
        );

        List<FilmsPerformResponse> cinemasPerformResponse = new LinkedList<>();
        map.forEach((key, value) ->
                cinemasPerformResponse.add(FilmsPerformResponse.builder()
                        .filmId(key)
                        .performs(value)
                        .build())
        );

        return cinemasPerformResponse;
    }


    public static List<CinemasPerformResponse> listCinemasResponse(List<Perform> performs) {
        HashMap<UUID, LinkedList<PerformDTO>> map = new HashMap<>();

        performs.forEach(perform ->
                {
                    if (map.containsKey(perform.getCinemaRoom().getId()))
                        map.get(perform.getCinemaRoom().getCinema().getId()).add(toDTO(perform));
                    else {
                        LinkedList<PerformDTO> list = new LinkedList<>();
                        list.add(toDTO(perform));
                        map.put(perform.getCinemaRoom().getCinema().getId(), list);
                    }
                }
        );

        List<CinemasPerformResponse> filmsPerformResponse = new LinkedList<>();

        map.forEach((key, value) ->
                filmsPerformResponse.add(CinemasPerformResponse.builder()
                        .cinemaId(key)
                        .performs(value)
                        .build())
        );

        return filmsPerformResponse;
    }

    public static PerformDTOItem toDtoITem(Perform perform) {

        return PerformDTOItem
                .builder()
                .filmDTO(FilmDTO.builder()
                        .id(perform.getFilm().getId())
                        .title(perform.getFilm().getTitle())
                        .duration(perform.getFilm().getDuration())
                        .description(perform.getFilm().getDescription())
                        .country(perform.getFilm().getCountry())
                        .director(perform.getFilm().getDirector())
                        .language(perform.getFilm().getLanguage())
                        .tags(perform.getFilm().getTags().stream()
                                .map(Tag::getName)
                                .collect(Collectors.toList()))
                        .description(perform.getFilm().getDescription())
                        .country(perform.getFilm().getCountry())
                        .director(perform.getFilm().getDirector())
                        .restrictAge(perform.getFilm().getRestrictAge())
                        .releaseDate(perform.getFilm().getReleaseDate())
                        .pictureUrl(perform.getFilm().getPictureUrl())
                        .trailerUrl(perform.getFilm().getTrailerUrl())
                        .build())
                .translateType(perform.getTranslateType())
                .viewType(perform.getViewType())
                .startTime(perform.getStartTime())
                .endTime(perform.getEndTime())
                .price(perform.getPrice())
                .id(perform.getId())
                .cinemaRoomDTO(CinemaRoomDTO.builder()
                        .id(perform.getCinemaRoom().getId())
                        .name(perform.getCinemaRoom().getName())
                        .cinemaId(perform.getCinemaRoom().getCinema().getId())
                        .build())
                .build();
    }


    //    public static Perform toEntity(PerformDTO performDTO) {
    //        TypeMap<PerformDTO, Perform> typeMap = new ModelMapper()
    //                .createTypeMap(PerformDTO.class, Perform.class);
    //        typeMap.addMappings(mapper -> {
    //            mapper.map(PerformDTO::getCinemaRoomDTO, Perform::setCinemaRoom);
    //            mapper.map(PerformDTO::getFilmDTO, Perform::setFilm);
    //            mapper.map(PerformDTO::getTranslateTypeDTO, Perform::setTranslateType);
    //        });
    //        return typeMap.map(performDTO);
    //
    //    }
}
