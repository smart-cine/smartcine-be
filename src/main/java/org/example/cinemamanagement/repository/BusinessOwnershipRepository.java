package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.BusinessOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessOwnershipRepository extends JpaRepository<BusinessOwnership, UUID> {
    @Query("SELECT p from BusinessOwnership p")
    List<BusinessOwnership> findAll();

    Optional<BusinessOwnership> findByOwnerId(UUID ownerId);

}
