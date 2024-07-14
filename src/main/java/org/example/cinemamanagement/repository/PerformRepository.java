package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Perform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@Repository
public interface PerformRepository extends JpaRepository<Perform, UUID> , JpaSpecificationExecutor<Perform> {

    @Query("SELECT p FROM Perform p WHERE p.cinemaRoom.id = :cinemaRoomId AND p.startTime <= :startTime AND (p.endTime >= :startTime OR p.startTime <= :endTime AND p.endTime >= :endTime) OR (p.startTime <= :endTime AND p.endTime >= :endTime)")
    List<Perform> TimeForPerformIsAvailable(UUID cinemaRoomId, Timestamp startTime, Timestamp endTime);
}
