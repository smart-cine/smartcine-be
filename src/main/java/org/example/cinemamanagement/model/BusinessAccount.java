package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "businessaccount")
public class BusinessAccount {

    @Id
    private UUID id;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @JsonIgnore
    @OneToOne(mappedBy = "businessAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BusinessProvider businessProviders;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.DETACH,
//            CascadeType.MERGE,
//            CascadeType.PERSIST,
//            CascadeType.REFRESH
//    })
//    @JoinTable(name = "_cinema_manager",
//            joinColumns = @JoinColumn(name = "manager_id"),
//            inverseJoinColumns = @JoinColumn(name = "cinema_id"))
//    private List<Cinema> cinemas;


//    @JsonIgnore
//    @OneToMany(mappedBy = "businessAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<CinemaLayout> cinemaLayouts;




}
