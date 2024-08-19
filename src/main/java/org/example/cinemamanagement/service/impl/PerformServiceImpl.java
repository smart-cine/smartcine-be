package org.example.cinemamanagement.service.impl;

import jakarta.persistence.EnumType;
import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.film.FilmDTO;
import org.example.cinemamanagement.dto.perform.PerformDTO;
import org.example.cinemamanagement.dto.perform.PerformDTOItem;
import org.example.cinemamanagement.mapper.PerformMapper;
import org.example.cinemamanagement.model.*;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.repository.*;
import org.example.cinemamanagement.repository.BusinessAccountRepository;
import org.example.cinemamanagement.service.PerformService;
import org.example.cinemamanagement.utils.ConvertJsonNameToTypeName;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PerformServiceImpl implements PerformService {
    PerformRepository performRepository;
    CinemaRoomRepository cinemaRoomRepository;
    FilmRepository filmRepository;
    BusinessAccountRepository managerAccountRepository;

    @Autowired
    public PerformServiceImpl(PerformRepository performRepository,
                              CinemaRoomRepository cinemaRoomRepository,
                              FilmRepository filmRepository,
                              BusinessAccountRepository managerAccountRepository
    ) {
        this.performRepository = performRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.filmRepository = filmRepository;
        this.managerAccountRepository = managerAccountRepository;
    }

    @Override
    public PageResponse<List<UUID>> getAllPerforms(
            PageSpecificationPerform<Perform> pageSpecification,
            CursorBasedPageable cursorBasedPageable) {

        var performSlide = performRepository.findAll(pageSpecification,
                Pageable.ofSize(cursorBasedPageable.getSize()));

        Map<String, Object> pagingMap = new HashMap<>();
        pagingMap.put("previousPageCursor", null);
        pagingMap.put("nextPageCursor", null);
        pagingMap.put("size", cursorBasedPageable.getSize());
        pagingMap.put("total", 0);
        if (performSlide.isEmpty()) {
            return new PageResponse<>(false, List.of(), pagingMap);
        }

        List<Perform> performs = performSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(performs.get(0).getStartTime(), performSlide.hasPrevious()));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(performs.get(performs.size() - 1).getStartTime(), performSlide.hasNext()));
        pagingMap.put("total", performSlide.getTotalElements());

//        return new PageResponse<>(true, performs.stream()
//                .map(PerformMapper::toDTO)
//                .collect(Collectors.toList()), pagingMap);
        return new PageResponse<>(true, performs.stream()
                .map(Perform::getId)
                .collect(Collectors.toList()), pagingMap);
    }

    @Override
    public PerformDTOItem getPerformById(UUID id) {
        Perform perform = performRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perform not found with id: " + id));

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

    @Override
    @Transactional
    public PerformDTO addPerform(AddPerformRequest addPerformRequest) {
        CinemaRoom cinemaRoom = cinemaRoomRepository
                .findById(addPerformRequest.getCinemaRoomId())
                .orElseThrow(() -> new RuntimeException("Cinema room not found"));
        Film film = filmRepository.findById(addPerformRequest.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Perform perform = performRepository.save(
                Perform.builder()
                        .film(film)
                        .cinemaRoom(cinemaRoom)
                        .viewType(addPerformRequest.getViewType())
                        .translateType(addPerformRequest.getTranslateType())
                        .startTime(addPerformRequest.getStartTime())
                        .endTime(addPerformRequest.getEndTime())
                        .price(addPerformRequest.getPrice())
                        .build()
        );

        return PerformMapper.toDTO(perform);
    }

    @Override
    public PerformDTO updatePerform(UUID id, Map<String, Object> payload) {
        Perform perform = performRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perform not found with id: " + id));

        for (Map.Entry<String, Object> dataSet : payload.entrySet()) {
            String key = dataSet.getKey();
            Object value = dataSet.getValue();

            try {

                key = ConvertJsonNameToTypeName.convert(key);

                if (key.equals("viewType") || key.equals("translateType")) {
//                    key = key.substring(0, 1).toUpperCase() + key.substring(1);

                    Field field = Perform.class.getDeclaredField("viewType");
                    System.out.println();

//                    Field field = Perform.class.getField(key);
//                    field.setAccessible(true);
//                    field.set(perform, EnumType.valueOf((String) value));
                } else {
//                    Field field = Perform.class.getDeclaredField(key);
//                    field.setAccessible(true);
//                    field.set(perform, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
//                throw new RuntimeException("Error updating field " + key);
            }
        }

        Perform updatedPerform = performRepository.save(perform);
        return PerformMapper.toDTO(updatedPerform);
    }

    @Override
    public void deletePerform(UUID id) {
        performRepository.deleteById(id);
    }


}