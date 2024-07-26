package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CinemaProviderRepository extends JpaRepository<CinemaProvider, UUID> {
}