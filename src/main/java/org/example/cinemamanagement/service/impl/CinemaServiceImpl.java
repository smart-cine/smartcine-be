package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.cinema.CinemaDTO;
import org.example.cinemamanagement.dto.cinema.CinemaLayoutDTO;
import org.example.cinemamanagement.dto.cinema.CinemaManagerDTO;
import org.example.cinemamanagement.mapper.CinemaMapper;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.payload.request.AddCinemaRequest;
import org.example.cinemamanagement.repository.*;
import org.example.cinemamanagement.service.CinemaService;
import org.example.cinemamanagement.utils.ConvertJsonNameToTypeName;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.pagination.PageSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    CinemaRepository cinemaRepository;
    AccountRepository accountRepository;
    CinemaLayoutRepository cinemaLayoutRepository;
    CinemaProviderRepository cinemaProviderRepository;

    BusinessAccountRepository managerAccountRepository;

    CinemaServiceImpl(CinemaRepository cinemaRepository, AccountRepository accountRepository, CinemaLayoutRepository cinemaLayoutRepository, CinemaProviderRepository cinemaProviderRepository, BusinessAccountRepository managerAccountRepository) {
        this.cinemaRepository = cinemaRepository;
        this.accountRepository = accountRepository;
        this.cinemaLayoutRepository = cinemaLayoutRepository;
        this.cinemaProviderRepository = cinemaProviderRepository;
        this.managerAccountRepository = managerAccountRepository;
    }

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
                .address(addCinemaRequest.getAddress())
                .cinemaProvider(cinemaProviderRepository.findById(addCinemaRequest.getProviderId())
                        .orElseThrow(() ->
                                new RuntimeException("Cinema Provider not found with id: " +
                                        addCinemaRequest.getProviderId())))
                .build();

        cinemaRepository.save(cinema);

        return CinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO updateCinema(UUID id, Map<String, Object> payload) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

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
//        Cinema cinema = cinemaRepository.findById(idCinema)
//                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + idCinema));
//
//        Account account = userRepository.findUserByEmail(emailUser)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + emailUser));
//
//        cinema.getCinemaManagers().remove(account);
//        cinemaRepository.save(cinema);
//
//        return CinemaManagerDTO
//                .builder()
//                .account(account)
//                .cinemas(account.getCinemas())
//                .build();
        return null;
    }


    @Override
    public List<CinemaLayoutDTO> getCinemaLayoutByCinemaId(UUID id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));

//        return cinema.getCinemaLayouts().stream()
//                .map(CinemaLayoutMapper::toDTO)
//                .collect(Collectors.toList());
        return null;
    }

    public PageResponse<List<CinemaDTO>> page(
            PageSpecification<Cinema> pageSpecification,
            CursorBasedPageable cursorBasedPageable) {

        var cinemaSlide = cinemaRepository.findAll(pageSpecification,
                Pageable.ofSize(cursorBasedPageable.getSize()));


        Map<String, String> pagingMap = new HashMap<>();
        pagingMap.put("previousPageCursor", null);
        pagingMap.put("nextPageCursor", null);
        pagingMap.put("size", String.valueOf(cursorBasedPageable.getSize()));
        if (!cinemaSlide.hasContent()) return new PageResponse<>(false, List.of(), pagingMap);

        List<Cinema> cinemas = cinemaSlide.getContent();
        pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(cinemas.get(0).getName(), cinemaSlide.hasPrevious()));
        pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(cinemas.get(cinemas.size() - 1).getName(), cinemaSlide.hasNext()));
        pagingMap.put("total", String.valueOf(cinemaSlide.getTotalElements()));

        return new PageResponse<>(true, cinemas.stream()
                .map(CinemaMapper::toDTO).collect(Collectors.toList()),
                pagingMap);
    }

}

