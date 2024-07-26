package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.CinemaProviderDTO;
import org.example.cinemamanagement.mapper.CinemaProviderMapper;
import org.example.cinemamanagement.model.CinemaProvider;
import org.example.cinemamanagement.repository.CinemaProviderRepository;
import org.example.cinemamanagement.service.CinemaProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CinemaProviderServiceImpl implements CinemaProviderService {

    private final CinemaProviderRepository cinemaProviderRepository;

    @Autowired
    public CinemaProviderServiceImpl(CinemaProviderRepository cinemaProviderRepository) {
        this.cinemaProviderRepository = cinemaProviderRepository;
    }

    @Override
    public List<CinemaProviderDTO> getAllCinemaProviders() {
        return cinemaProviderRepository.findAll().stream()
                .map(CinemaProviderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaProviderDTO getCinemaProviderById(UUID id) {
        return cinemaProviderRepository.findById(id)
                .map(CinemaProviderMapper::toDTO)
                .orElse(null);
    }

    @Override
    public CinemaProviderDTO saveCinemaProvider(CinemaProviderDTO cinemaProviderDTO) {
        CinemaProvider cinemaProvider = CinemaProviderMapper.toEntity(cinemaProviderDTO);
        CinemaProvider savedCinemaProvider = cinemaProviderRepository.save(cinemaProvider);
        return CinemaProviderMapper.toDTO(savedCinemaProvider);
    }

    @Override
    public void deleteCinemaProvider(UUID id) {
        cinemaProviderRepository.deleteById(id);
    }
}