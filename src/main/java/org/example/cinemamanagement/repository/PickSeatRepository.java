package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.PickSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PickSeatRepository extends JpaRepository<PickSeat, UUID> {
    List<PickSeat> findByAccountId(UUID accountId);


    List<PickSeat> findByPerformId(UUID performId);


    @Query("SELECT p FROM PickSeat p WHERE p.perform.id= ?1")
    List<PickSeat> findAllByPerformId(UUID performID);
}
