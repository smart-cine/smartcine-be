package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cinemaprovider")
public class CinemaProvider {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "background_url")
    private String backgroundUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cinema> cinemas;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Film> films;
}
