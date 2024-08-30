package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.OwnerShipTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerShipTreeRepository extends JpaRepository<OwnerShipTree, UUID> {
    @Query(value = "select * from _ownership_tree where item_id = ?1", nativeQuery = true)
    Optional<OwnerShipTree> findByItemId(UUID itemId);
}
