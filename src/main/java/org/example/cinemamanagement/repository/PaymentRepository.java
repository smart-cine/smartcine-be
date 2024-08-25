package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Payment;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    String sql1 = "SELECT p FROM Payment p WHERE p.vnpTxnRef = ?1 AND p.dateCreate < ?2 ORDER BY p.dateCreate DESC LIMIT 1";

    @Query(value = "select p from Payment p where p.dateExpired > ?1  order by p.dateExpired desc limit 1")
    Payment findByVnpTxnRefAndDateCreate(Timestamp datePay);

    Payment findAllByBankType(String bankType);

    @Query(value = "select * from payment where payment.account_id = ?1 and payment.perform_id = ?2 and payment.status = 'PENDING' ", nativeQuery = true)
    Optional<Payment> existsPaymentWithPendingStatusOfMyOwn(UUID accountId, UUID performId);

    @Query(value = "select *  from payment where payment.type = 'VNPAY' and JSON_EXTRACT(data, '$.vnp_TxnRef') = ?1 ", nativeQuery = true)
    Optional<Payment> findByVnpTxnRef(String vnpTxnRef);
}
