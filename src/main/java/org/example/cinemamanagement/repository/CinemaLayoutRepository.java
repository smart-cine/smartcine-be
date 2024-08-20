package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.CinemaLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CinemaLayoutRepository extends JpaRepository<CinemaLayout, UUID>, JpaSpecificationExecutor<CinemaLayout> {
}
