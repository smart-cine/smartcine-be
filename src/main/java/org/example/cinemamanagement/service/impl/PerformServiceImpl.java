package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.PerformDTO;
import org.example.cinemamanagement.mapper.PerformMapper;
import org.example.cinemamanagement.model.*;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.repository.*;
import org.example.cinemamanagement.service.PerformService;
import org.example.cinemamanagement.utils.ConvertJsonNameToTypeName;
import org.example.cinemamanagement.utils.CursorBasedPageable;
import org.example.cinemamanagement.utils.PageResponse;
import org.example.cinemamanagement.utils.PageSpecification;
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

    @Autowired
    public PerformServiceImpl(PerformRepository performRepository,
                              CinemaRoomRepository cinemaRoomRepository,
                              FilmRepository filmRepository
    ) {
        this.performRepository = performRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public PageResponse<List<PerformDTO>> getAllPerforms(
            PageSpecificationPerform<Perform> pageSpecification,
            CursorBasedPageable cursorBasedPageable) {

        var performSlide = performRepository.findAll(pageSpecification,
                Pageable.ofSize(cursorBasedPageable.getSize()));

        Map<String, Object> pagingMap = new HashMap<>();
        pagingMap.put("previousPageCursor", null);
        pagingMap.put("nextPageCursor", null);
        pagingMap.put("size", cursorBasedPageable.getSize());
        pagingMap.put("total", performSlide.getTotalElements());
        if (performSlide.isEmpty()) {
            return new PageResponse<>(false, List.of(), pagingMap);
        }

        List<Perform> performs = performSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(performs.get(0).getStartTime(), performSlide.hasPrevious()));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(performs.get(performs.size() - 1).getStartTime(), performSlide.hasNext()));


        return new PageResponse<>(true, performs.stream()
                .map(PerformMapper::toDTO)
                .collect(Collectors.toList()), pagingMap);


    }

    @Override
    public PerformDTO getPerformById(UUID id) {

        Perform perform = performRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perform not found with id: " + id));
        return PerformMapper.toDTO(perform);
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

        for(Map.Entry<String, Object> dataSet : payload.entrySet())
        {
            String key = dataSet.getKey();
            Object value = dataSet.getValue();

            try {
                Field field = Cinema.class.getDeclaredField(
                        ConvertJsonNameToTypeName.convert(key)
                );

                field.setAccessible(true);
                field.set(perform, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field " + key);
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