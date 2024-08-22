package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.common.BankType;
import org.example.cinemamanagement.mapper.JpaConverterJson;
import org.example.cinemamanagement.mapper.JsonToMapConverter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "businessbank")
public class BusinessBank {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_provider_id")
    private CinemaProvider cinemaProvider;

    @JsonIgnore
    @OneToMany(mappedBy = "businessBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @JsonIgnore
    @OneToMany(mappedBy = "businessBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankCinema> bankCinemas;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BankType type;

    @Column(name = "data", columnDefinition = "json")
    private String data;

}
