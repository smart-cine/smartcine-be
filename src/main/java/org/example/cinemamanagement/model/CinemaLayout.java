package org.example.cinemamanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.LayoutType;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinemalayout")
public class CinemaLayout {

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cinemaLayout")
    private List<CinemaRoom> cinemaRooms;


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String data;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LayoutType layoutType;


    @ManyToOne( fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "manager_id")
    private ManagerAccount managerAccount;


    public void addCinemaRoom(CinemaRoom cinemaRoom) {
        if (this.cinemaRooms == null) {
            this.cinemaRooms = new ArrayList<>();
        }
        this.cinemaRooms.add(cinemaRoom);
    }
}
