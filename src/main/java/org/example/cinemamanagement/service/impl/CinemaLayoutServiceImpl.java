package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutGroupDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutSeatDTOItem;
import org.example.cinemamanagement.mapper.CinemaLayoutMapper;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.model.CinemaLayoutGroup;
import org.example.cinemamanagement.model.CinemaLayoutSeat;
import org.example.cinemamanagement.model.CinemaProvider;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.pagination.PagingModel;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.repository.CinemaLayoutGroupRepository;
import org.example.cinemamanagement.repository.CinemaLayoutRepository;
import org.example.cinemamanagement.repository.CinemaLayoutSeatRepository;
import org.example.cinemamanagement.repository.CinemaProviderRepository;
import org.example.cinemamanagement.service.CinemaLayoutService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CinemaLayoutServiceImpl implements CinemaLayoutService {

    private CinemaLayoutRepository cinemaLayoutRepository;
    private CinemaProviderRepository cinemaProviderRepository;
    private CinemaLayoutGroupRepository cinemaLayoutGroupRepository;
    private CinemaLayoutSeatRepository cinemaLayoutSeatRepository;


    public CinemaLayoutServiceImpl(CinemaLayoutRepository cinemaLayoutRepository,
                                   CinemaProviderRepository cinemaProviderRepository,
                                   CinemaLayoutGroupRepository cinemaLayoutGroupRepository,
                                   CinemaLayoutSeatRepository cinemaLayoutSeatRepository
    ) {
        this.cinemaLayoutRepository = cinemaLayoutRepository;
        this.cinemaProviderRepository = cinemaProviderRepository;
        this.cinemaLayoutGroupRepository = cinemaLayoutGroupRepository;
        this.cinemaLayoutSeatRepository = cinemaLayoutSeatRepository;
    }

    @Override
    public PageResponse<List<CinemaLayoutDTO>> getAllCinemaLayout
            (Specification<CinemaLayout> specification, CursorBasedPageable cursorBasedPageable) {

        var cinemaLayoutSlide = cinemaLayoutRepository.findAll(
                specification, Pageable.ofSize(cursorBasedPageable.getLimit()));


        PagingModel pagingMap = PagingModel.builder()
                .limit(cursorBasedPageable.getLimit())
                .total(cinemaLayoutSlide.getTotalElements())
                .build();

        if (cinemaLayoutSlide.isEmpty()) {
            return new PageResponse<List<CinemaLayoutDTO>>(false, List.of(), pagingMap);
        }

        List<CinemaLayout> cinemaLayoutList = cinemaLayoutSlide.getContent();
        pagingMap.setPreviousPageCursor(cursorBasedPageable.getEncodedCursor(cinemaLayoutList.get(0).getId(), cinemaLayoutSlide.hasPrevious()));
        pagingMap.setNextPageCursor(cursorBasedPageable.getEncodedCursor(cinemaLayoutList.get(cinemaLayoutList.size() - 1).getId(), cinemaLayoutSlide.hasNext()));
        return new PageResponse<List<CinemaLayoutDTO>>(true, cinemaLayoutList.stream().map(CinemaLayoutMapper::toDTO).collect(Collectors.toList()), pagingMap);

    }

    @Override
    public CinemaLayoutDTOItem getCinemaLayout(UUID id) {
        CinemaLayout layout = cinemaLayoutRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cinema layout not found with id: " + id));

        return CinemaLayoutMapper.toDTOItem(layout);
    }

    @Override
    @Transactional
    public CinemaLayoutDTO addCinemaLayout(AddCinemaLayoutRequest cinemaLayoutRequest) {
        CinemaProvider cinemaProvider = cinemaProviderRepository.findById(cinemaLayoutRequest.getProviderId())
                .orElseThrow(() ->
                        new RuntimeException("Cinema provider not found with id: " + cinemaLayoutRequest.getProviderId()));

        CinemaLayout layout = CinemaLayout.builder()
                .columns(cinemaLayoutRequest.getColumns())
                .rows(cinemaLayoutRequest.getRows())
                .cinemaProvider(cinemaProvider)
                .build();

        cinemaLayoutRepository.save(layout);
        return CinemaLayoutMapper.toDTO(layout);
    }

    @Override
    @Transactional
    public CinemaLayoutDTO updateCinemaLayout(UUID idLayout, CinemaLayoutDTO cinemaLayoutDTO) {
        CinemaLayout layout = cinemaLayoutRepository.findById(idLayout)
                .orElseThrow(() ->
                        new RuntimeException("Cinema layout not found with id: " + idLayout));

        try {

            if (cinemaLayoutDTO.getColumns() != null) {
                Integer rows = layout.getRows();
                Integer columns = layout.getColumns();

                if (cinemaLayoutDTO.getColumns() < columns) {
                }

                else if (cinemaLayoutDTO.getColumns() > columns) {

                }


            }



            if (cinemaLayoutDTO.getRows() != null) {
                layout.setRows(cinemaLayoutDTO.getRows());
            }

            cinemaLayoutRepository.save(layout);
            return CinemaLayoutMapper.toDTO(layout);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error : " + e);
        }
    }

    @Override
    @Transactional
    public void deleteCinemaLayout(UUID id) {
        cinemaLayoutRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cinema layout not found with id: " + id));

        cinemaLayoutRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CinemaLayoutDTOItem cloneCinemaLayout(UUID idLayout) {
        try {
            LinkedList<CinemaLayoutGroupDTOItem> groups = new LinkedList<>();

            CinemaLayout layout = cinemaLayoutRepository.findById(idLayout)
                    .orElseThrow(() ->
                            new RuntimeException("Cinema layout not found with id: " + idLayout));

            CinemaLayout cloneLayout = CinemaLayout.builder()
                    .cinemaProvider(layout.getCinemaProvider())
                    .columns(layout.getColumns())
                    .rows(layout.getRows())
                    .build();
            cinemaLayoutRepository.save(cloneLayout);

            List<CinemaLayoutSeat> seats = layout.getCinemaLayoutSeats().stream().map(
                    seat -> {
                        CinemaLayoutGroup group = seat.getCinemaLayoutGroup();

                        CinemaLayoutGroup newGroup = cinemaLayoutGroupRepository.save(
                                CinemaLayoutGroup.builder()
                                        .name(group.getName())
                                        .color(group.getColor())
                                        .price(group.getPrice())
                                        .cinemaLayout(cloneLayout)
                                        .build());

                        groups.add(CinemaLayoutGroupDTOItem.builder()
                                .id(newGroup.getId())
                                .name(newGroup.getName())
                                .color(newGroup.getColor())
                                .build());

                        return CinemaLayoutSeat.builder()
                                .x(seat.getX())
                                .y(seat.getY())
                                .cinemaLayoutGroup(newGroup)
                                .cinemaLayout(cloneLayout)
                                .code(seat.getCode())
                                .build();
                    }
            ).toList();

            cinemaLayoutSeatRepository.saveAll(seats);

            return CinemaLayoutDTOItem.builder()
                    .id(cloneLayout.getId())
                    .columns(cloneLayout.getColumns())
                    .rows(cloneLayout.getRows())
                    .providerId(cloneLayout.getCinemaProvider().getId())
                    .seats(seats.stream().map(seat -> {
                        return CinemaLayoutSeatDTOItem.builder()
                                .id(seat.getId())
                                .x(seat.getX())
                                .y(seat.getY())
                                .code(seat.getCode())
                                .groupId(seat.getCinemaLayoutGroup().getId())
                                .build();
                    }).toList())
                    .groups(groups)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error : " + e);
        }

    }
}
