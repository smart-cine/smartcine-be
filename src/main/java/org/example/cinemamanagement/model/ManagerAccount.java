package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manageraccount")
public class ManagerAccount {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "Account",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinTable(name = "_cinema_manager",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id"))
    private List<Cinema> cinemas;


    @JsonIgnore
    @OneToMany(mappedBy = "managerAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Film> films;


    @JsonIgnore
    @OneToMany(mappedBy = "managerAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CinemaLayout> cinemaLayouts;

    @OneToMany(mappedBy = "managerAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Perform> performs;
}
