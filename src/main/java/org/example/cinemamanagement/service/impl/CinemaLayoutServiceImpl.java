package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.CinemaLayoutDTO;
import org.example.cinemamanagement.mapper.CinemaLayoutMapper;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.payload.request.AddCinemaLayoutRequest;
import org.example.cinemamanagement.repository.CinemaLayoutRepository;
import org.example.cinemamanagement.service.CinemaLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CinemaLayoutServiceImpl implements CinemaLayoutService {

    @Autowired
    private CinemaLayoutRepository cinemaLayoutRepository;

    @Override
    public List<CinemaLayoutDTO> getAllCinemaLayout() {
        return cinemaLayoutRepository.findAll()
                .stream()
                .map(CinemaLayoutMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaLayoutDTO getCinemaLayout(UUID id) {
        CinemaLayout layout = cinemaLayoutRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cinema layout not found with id: " + id));
        return CinemaLayoutMapper.toDTO(layout);
    }

    @Override
    @Transactional
    public CinemaLayoutDTO addCinemaLayout(AddCinemaLayoutRequest cinemaLayoutRequest) {


        CinemaLayout layout = CinemaLayout.builder()
                .columns(cinemaLayoutRequest.getColumns())
                .rows(cinemaLayoutRequest.getRows())
                .managerAccount(null)
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

        Field[] fields = cinemaLayoutDTO.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(cinemaLayoutDTO);
                if (value != null) {
                    field.set(layout, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        cinemaLayoutRepository.save(layout);
        return CinemaLayoutMapper.toDTO(layout);
    }

    @Override
    @Transactional
    public void deleteCinemaLayout(UUID id) {
        cinemaLayoutRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cinema layout not found with id: " + id));

        cinemaLayoutRepository.deleteById(id);
    }
}
