package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.BusinessBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessBankRepository extends JpaRepository<BusinessBank, UUID> {

}
