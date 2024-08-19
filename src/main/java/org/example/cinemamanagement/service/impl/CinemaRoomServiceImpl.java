package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.cinema.CinemaRoomDTO;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutGroupDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaLayoutSeatDTOItem;
import org.example.cinemamanagement.dto.cinema.item.CinemaRoomDTOItem;
import org.example.cinemamanagement.mapper.CinemaRoomMapper;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.payload.request.AddOrUpdateCinemaRoom;
import org.example.cinemamanagement.repository.CinemaLayoutRepository;
import org.example.cinemamanagement.repository.CinemaRepository;
import org.example.cinemamanagement.repository.CinemaRoomRepository;
import org.example.cinemamanagement.service.CinemaRoomService;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;
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
public class CinemaRoomServiceImpl implements CinemaRoomService {

    public CinemaLayoutRepository cinemaLayoutRepository;
    public CinemaRepository cinemaRepository;

    public CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    public CinemaRoomServiceImpl(CinemaLayoutRepository cinemaLayoutRepository, CinemaRepository cinemaRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.cinemaLayoutRepository = cinemaLayoutRepository;
        this.cinemaRepository = cinemaRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    @Override
    public PageResponse<List<CinemaRoomDTO>> getAllCinemaRooms(CursorBasedPageable cursorBasedPageable, PageSpecification<CinemaRoom> specification) {
        var cinemaRoomSlide = cinemaRoomRepository.findAll(specification,
                Pageable.ofSize(cursorBasedPageable.getSize()));

        Map<String, Object> pagingMap = new HashMap<>();

        pagingMap.put("previousPageCursor", null);
        pagingMap.put("nextPageCursor", null);
        pagingMap.put("size", cursorBasedPageable.getSize());
        pagingMap.put("total", cinemaRoomSlide.getTotalElements());

        if (!cinemaRoomSlide.hasContent()) {
            return new PageResponse<>(false, List.of(), pagingMap);
        }

        List<CinemaRoom> cinemaRooms = cinemaRoomSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(cinemaRooms.get(0).getName(), cinemaRoomSlide.hasPrevious()));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(cinemaRooms.get(cinemaRooms.size() - 1).getName(), cinemaRoomSlide.hasNext()));

        return new PageResponse<>(true, cinemaRooms.stream()
                .map(CinemaRoomMapper::toDTO).collect(Collectors.toList()),
                pagingMap);
    }


    @Override
    public CinemaRoomDTOItem getCinemaRoomById(UUID id) {
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CinemaRoom not found"));

        Map<String, Object> layoutResponse = new HashMap<>();

        return CinemaRoomDTOItem.builder()
                .id(cinemaRoom.getId())
                .cinemaId(cinemaRoom.getCinema().getId())
                .name(cinemaRoom.getName())
                .layout(CinemaLayoutDTOItem.builder()
                        .id(cinemaRoom.getCinemaLayout().getId())
                        .rows(cinemaRoom.getCinemaLayout().getRows())
                        .columns(cinemaRoom.getCinemaLayout().getColumns())
                        .groups(cinemaRoom.getCinemaLayout().getCinemaLayoutGroups().stream().map(group -> CinemaLayoutGroupDTOItem.builder()
                                .id(group.getId())
                                .name(group.getName())
                                .color(group.getColor())
                                .build()).collect(Collectors.toList())
                        )
                        .seats(
                                cinemaRoom.getCinemaLayout().getCinemaLayoutSeats().stream().map(seat -> CinemaLayoutSeatDTOItem.builder()
                                        .id(seat.getId())
                                        .groupId(seat.getCinemaLayoutGroup().getId())
                                        .code(seat.getCode())
                                        .x(seat.getX())
                                        .y(seat.getY())
                                        .build()).collect(Collectors.toList()
                                ))
                        .build())
                .build();
    }

    @Override
    @Transactional
    public CinemaRoomDTO addCinemaRoom(AddOrUpdateCinemaRoom addOrUpdateCinemaRoom) {
        Cinema cinema = cinemaRepository.findById(addOrUpdateCinemaRoom.getCinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

        CinemaLayout layout = cinemaLayoutRepository.findById(addOrUpdateCinemaRoom.getCinemaLayoutId())
                .orElseThrow(() -> new RuntimeException("Cinema layout not found"));


        CinemaRoom cinemaRoom = CinemaRoom.builder()
                .name(addOrUpdateCinemaRoom.getName())
                .cinema(cinema)
                .cinemaLayout(layout)
                .build();

        cinemaRoomRepository.save(cinemaRoom);
        return CinemaRoomMapper.toDTO(cinemaRoom);
    }

    @Override
    public CinemaRoomDTO updateCinemaRoom(UUID id, AddOrUpdateCinemaRoom addOrUpdateCinemaRoom) {
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CinemaRoom not found"));


        Field[] fields = addOrUpdateCinemaRoom.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(addOrUpdateCinemaRoom) != null) {
                    Field cinemaRoomField = cinemaRoom.getClass().getDeclaredField(field.getName());
                    cinemaRoomField.setAccessible(true);
                    cinemaRoomField.set(cinemaRoom, field.get(addOrUpdateCinemaRoom));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        cinemaRoomRepository.save(cinemaRoom);

        return CinemaRoomMapper.toDTO(cinemaRoom);
    }

    @Override
    public void deleteCinemaRoom(UUID id) {
        cinemaRoomRepository.deleteById(id);
    }
}