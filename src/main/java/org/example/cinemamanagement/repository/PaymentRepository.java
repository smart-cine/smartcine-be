package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    String sql1 = "SELECT p FROM Payment p WHERE p.vnpTxnRef = ?1 AND p.dateCreate < ?2 ORDER BY p.dateCreate DESC LIMIT 1";

    @Query(value = "select p from Payment p where p.dateExpire > ?2 and p.vnpTxnRef = ?1 order by p.dateExpire desc limit 1")
    Payment findByVnpTxnRefAndDateCreate(String vnpTxnRef, Timestamp datePay);

    @Query(value = "select p from Payment p where p.account.id = ?1 and p.perform.id = ?2 order by p.dateCreate desc limit 1")
    Payment findTheLastPaymentByAccountIdAndPerformId(UUID accountId, UUID performId);
}
