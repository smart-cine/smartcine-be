package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, UUID>, JpaSpecificationExecutor<CinemaRoom> {
    Optional<CinemaRoom> findByName(String name);
    @Query("select c_r from CinemaRoom c_r where c_r.name = ?1 and c_r.cinema.id = ?2")
    Optional<CinemaRoom> findByNameAndCinemaId(String name, UUID cinemaId);
}


