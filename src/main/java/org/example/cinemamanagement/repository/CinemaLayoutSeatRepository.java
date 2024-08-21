package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaLayoutSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CinemaLayoutSeatRepository extends JpaRepository<CinemaLayoutSeat, UUID> {
    @Query(value = "DELETE FROM CinemaLayoutSeat c WHERE c.x > ?1 and c.cinemaLayout.id = ?2")
    void deleteSeatHaveXGreaterThan(Integer x, UUID cinemaLayoutId);
}
