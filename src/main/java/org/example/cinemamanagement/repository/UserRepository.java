package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Account, UUID> {
    public Optional<Account> findUserByEmail(String email);

    public boolean existsByEmail(String email);

}
