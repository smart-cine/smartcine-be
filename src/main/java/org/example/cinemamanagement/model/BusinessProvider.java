package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.common.RoleCinema;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_business_provider")
public class BusinessProvider {
    @Id
    @OneToOne
    @JoinColumn(name = "account_id")
    private BusinessAccount businessAccount;

    @Id
    @ManyToOne
    @JoinColumn(name = "cinema_provider_id")
    private CinemaProvider cinemaProvider;

    @Enumerated(EnumType.STRING)
    private RoleCinema role;
}
