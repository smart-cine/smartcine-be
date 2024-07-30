package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaLayoutSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository public interface CinemaLayoutSeatRepository extends JpaRepository<CinemaLayoutSeat, UUID> {
}
