package org.example.cinemamanagement.repository;

import org.example.cinemamanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Account, UUID> {

    String sql1 = """
                        SELECT c.variant, c.name, Sum(p.amount) AS total
                        FROM cinema_management.payment p
                        JOIN cinema_management.cinema_manager cm ON cm.cinema_id = p.cinema_id
                        JOIN cinema_management.cinema c on c.id = cm.cinema_id
                        WHERE cm.user_id = ?1
                            AND p.date_create BETWEEN ?2 AND ?3
                        GROUP BY cm.cinema_id
                 """;
    @Query(value = sql1, nativeQuery = true)
    public List<Object[]> getTotalAmountOfCinemaInMonth(UUID managerId, LocalDate thirtyDaysAgo, LocalDate today);

    String sql2 = """
                        SELECT f.director, f.title, sum(sp.price) AS total
                        FROM cinema_management.film f
                        JOIN cinema_management.perform pf ON pf.film_id = f.id
                        JOIN cinema_management.pick_seat ps ON ps.perform_id = pf.id
                        JOIN cinema_management.seat_price sp ON (sp.x = ps.x)
                                                             AND (sp.y = ps.y)
                                                             AND (sp.perform_id = ps.perform_id) 
                        WHERE pf.start_time BETWEEN ?1 AND ?2
                        GROUP BY f.country, f.director, f.title
                  """;
    @Query(value = sql2, nativeQuery = true)
    public List<Object[]> getAmountOfFilm(LocalDate thirtyDaysAgo, LocalDate today);


}
