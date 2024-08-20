package org.example.cinemamanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinemalayout")
public class CinemaLayout {


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private Integer rows;

    private Integer columns;


    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cinema_provider_id")
    private CinemaProvider cinemaProvider;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaLayout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CinemaLayoutGroup> cinemaLayoutGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaLayout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CinemaLayoutSeat> cinemaLayoutSeats;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cinemaLayout")
    private List<CinemaRoom> cinemaRooms;
}
