package org.example.cinemamanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Tag {

    @Id
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH
    }, fetch = FetchType.LAZY)
    private List<Film> films;

}
