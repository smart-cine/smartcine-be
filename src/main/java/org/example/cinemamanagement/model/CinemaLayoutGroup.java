package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cinemalayoutgroup")
public class CinemaLayoutGroup {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cinema_layout_id")
    private CinemaLayout cinemaLayout;

    private String name;
    private Integer color;
    private Double price;

    @OneToMany(mappedBy = "cinemaLayoutGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CinemaLayoutSeat> cinemaLayoutSeats;
}