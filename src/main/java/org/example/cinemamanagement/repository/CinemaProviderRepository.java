package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CinemaProviderRepository extends JpaRepository<CinemaProvider, UUID> {

    @Query(value = "SELECT 1", nativeQuery = true)
    Optional<Object> testing();

    @Query(value = "SELECT * FROM cinemaprovider", nativeQuery = true)
    List<CinemaProvider> findAll();
}