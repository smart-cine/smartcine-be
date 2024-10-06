package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.film.FilmDTO;
import org.example.cinemamanagement.dto.perform.PerformDTO;
import org.example.cinemamanagement.dto.perform.PerformDTOItem;
import org.example.cinemamanagement.mapper.PerformMapper;
import org.example.cinemamanagement.model.*;
import org.example.cinemamanagement.pagination.PageSpecificationPerform;
import org.example.cinemamanagement.pagination.PagingModel;
import org.example.cinemamanagement.payload.request.AddPerformRequest;
import org.example.cinemamanagement.payload.response.FilmsPerformResponse;
import org.example.cinemamanagement.payload.response.CinemasPerformResponse;
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
                Pageable.ofSize(cursorBasedPageable.getLimit()));

        PagingModel pagingModel = PagingModel.builder()
                .total(0)
                .limit(cursorBasedPageable.getLimit())
                .build();

        if (performSlide.isEmpty()) {
            return new PageResponse<>(false, List.of(), pagingModel);
        }

        List<Perform> performs = performSlide.getContent();

        pagingModel.setNextPageCursor(cursorBasedPageable
                .getEncodedCursor(performs
                        .get(performs.size() - 1).getStartTime(), performSlide.hasNext()
                ));
        pagingModel.setTotal(performSlide.getTotalElements());

        return new PageResponse<>(true, performs.stream()
                .map(Perform::getId)
                .collect(Collectors.toList()), pagingModel);
    }

    @Override
    public PerformDTOItem getPerformById(UUID id) {
        Perform perform = performRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perform not found with id: " + id));

        return PerformMapper.toDtoITem(perform);
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

    @Override
    public PageResponse<List<FilmsPerformResponse>> getPerformsByCinema(PageSpecificationPerform<Perform> pageSpecification, CursorBasedPageable cursorBasedPageable) {
        {
            var performSlide = performRepository.findAll(pageSpecification,
                    Pageable.ofSize(cursorBasedPageable.getLimit()));

            PagingModel paging = PagingModel.builder()
                    .previousPageCursor(null)
                    .nextPageCursor(null)
                    .limit(cursorBasedPageable.getLimit())
                    .total(0)
                    .build();

            if (!performSlide.hasContent())
                return new PageResponse<List<FilmsPerformResponse>>(false, List.of(), paging);

            List<Perform> performs = performSlide.getContent();

            paging.setNextPageCursor(cursorBasedPageable.getEncodedCursor(performs.get(performs.size() - 1).getStartTime(), performSlide.hasNext()));
            paging.setPreviousPageCursor(cursorBasedPageable.getEncodedCursor(performs.get(0).getStartTime(), performSlide.hasPrevious()));
            paging.setTotal(performSlide.getTotalElements());

            return new PageResponse<>(true, PerformMapper.listFilmsResponse(performs), paging);
        }
    }

    @Override
    public PageResponse<List<CinemasPerformResponse>> getPerformsByFilm(PageSpecificationPerform<Perform> pageSpecification, CursorBasedPageable cursorBasedPageable) {
        {
            var performSlide = performRepository.findAll(pageSpecification,
                    Pageable.ofSize(cursorBasedPageable.getLimit()));

            PagingModel paging = PagingModel.builder()
                    .limit(cursorBasedPageable.getLimit())
                    .total(0)
                    .build();

            if (!performSlide.hasContent())
                return new PageResponse<List<CinemasPerformResponse>>(false, List.of(), paging);

            List<Perform> performs = performSlide.getContent();

            paging.setNextPageCursor(cursorBasedPageable.getEncodedCursor(performs.get(performs.size() - 1).getStartTime(), performSlide.hasNext()));
            paging.setTotal(performSlide.getTotalElements());

            return new PageResponse<>(true, PerformMapper.listCinemasResponse(performs), paging);
        }
    }
}