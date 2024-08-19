package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.BankType;
import org.springframework.context.annotation.Primary;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_bank_cinema")
public class BankCinema {
    @Id
    @ManyToOne
    @JoinColumn(name = "business_bank_id")
    private BusinessBank businessBank;

    @Id
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BankType bankType;

}
