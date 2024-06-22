package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    public Optional<Tag> findByName(String name);
}
