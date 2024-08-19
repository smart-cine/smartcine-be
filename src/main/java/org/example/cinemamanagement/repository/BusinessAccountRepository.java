package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.BusinessAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessAccountRepository extends JpaRepository<BusinessAccount, UUID> {
}
