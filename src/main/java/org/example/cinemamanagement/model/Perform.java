package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.common.TranslateType;
import org.example.cinemamanagement.common.ViewType;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Perform")
public class Perform {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}
    )
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "view_type")
    @Enumerated(EnumType.STRING)
    private ViewType viewType;

    @Column(name = "translate_type")
    @Enumerated(EnumType.STRING)
    private TranslateType translateType;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}
    )
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;

    @JsonIgnore
    @OneToMany(mappedBy = "perform", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PickSeat> pickSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "perform", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "price")
    private Double price;
}
