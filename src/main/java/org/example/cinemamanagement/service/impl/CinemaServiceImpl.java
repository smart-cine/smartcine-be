package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.CinemaDTO;
import org.example.cinemamanagement.dto.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.CinemaManagerDTO;
import org.example.cinemamanagement.mapper.CinemaLayoutMapper;
import org.example.cinemamanagement.mapper.CinemaMapper;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.model.User;
import org.example.cinemamanagement.payload.request.AddCinemaRequest;
import org.example.cinemamanagement.repository.CinemaLayoutRepository;
import org.example.cinemamanagement.repository.CinemaRepository;
import org.example.cinemamanagement.repository.UserRepository;
import org.example.cinemamanagement.service.CinemaService;
import org.example.cinemamanagement.utils.ConvertJsonNameToTypeName;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CinemaLayoutRepository cinemaLayoutRepository;

    @Override
    public List<CinemaDTO> getAllCinema() {
        return cinemaRepository.findAll()
                .stream()
                .map(CinemaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDTO getCinema(UUID id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));
        return CinemaMapper.toDTO(cinema);
    }

    // check
    @Override
    public CinemaDTO addCinema(AddCinemaRequest addCinemaRequest) {
        Cinema cinema = Cinema.builder()
                .name(addCinemaRequest.getName())
                .variant(addCinemaRequest.getVariant())
                .build();

        cinemaRepository.save(cinema);

        return CinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO updateCinema(UUID id, Map<String, Object> payload) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                Field field = Cinema.class.getDeclaredField(
                        ConvertJsonNameToTypeName.convert(key)
                );

                field.setAccessible(true);
                field.set(cinema, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field " + key);
            }
        }
        Cinema updatedCinema = cinemaRepository.save(cinema);
        return CinemaMapper.toDTO(updatedCinema);

    }

    @Override
    public void deleteCinema(UUID id) {
        cinemaRepository.deleteById(id);
    }

    // check
    @Override
    public CinemaManagerDTO deleteCinemaManagerOutOfCinema(String emailUser, UUID idCinema) {
        Cinema cinema = cinemaRepository.findById(idCinema)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + idCinema));

        User user = userRepository.findUserByEmail(emailUser)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + emailUser));

        cinema.getCinemaManagers().remove(user);
        cinemaRepository.save(cinema);

        return CinemaManagerDTO
                .builder()
                .user(user)
                .cinemas(user.getCinemas())
                .build();
    }



    @Override
    public List<CinemaLayoutDTO> getCinemaLayoutByCinemaId(UUID id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));

        return cinema.getCinemaLayouts().stream()
                .map(CinemaLayoutMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PageResponse<List<CinemaDTO>> page(
            PageSpecification<Cinema> pageSpecification,
            CursorBasedPageable cursorBasedPageable) {

        var cinemaSlide = cinemaRepository.findAll(pageSpecification,
                Pageable.ofSize(cursorBasedPageable.getSize()));



        if (!cinemaSlide.hasContent()) return new PageResponse<>(false, null, null);
        Map<String, String> pagingMap = new HashMap<>();

        List<Cinema> cinemas = cinemaSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(cinemas.get(0).getName(), cinemaSlide.hasPrevious()));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(cinemas.get(cinemas.size() - 1).getName(), cinemaSlide.hasNext()));
        pagingMap.put("size", String.valueOf(cursorBasedPageable.getSize()));
        pagingMap.put("total", String.valueOf(cinemaSlide.getTotalElements()));

        return new PageResponse<>(true, cinemas.stream()
                .map(CinemaMapper::toDTO).collect(Collectors.toList()),
                pagingMap);
    }

}

