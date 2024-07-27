package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cinema")
public class Cinema {

    @JsonIgnore
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CinemaRoom> cinemaRooms;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String address;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH}
    )
    @JoinColumn(name = "provider_id")
    private CinemaProvider cinemaProvider;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "_cinema_manager",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id"))
    private List<ManagerAccount> managerAccounts;

}
