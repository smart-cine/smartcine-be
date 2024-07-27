package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.ManagerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerAccountRepository extends JpaRepository<ManagerAccount, UUID> {
}
