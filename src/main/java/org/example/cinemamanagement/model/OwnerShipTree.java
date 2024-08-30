package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.mapper.JpaConverterJson;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_ownership_tree")
public class OwnerShipTree {
    @Id
    @Column(name = "item_id")
    private UUID itemId;
    @Column(name = "parent_id")
    private UUID parentId;
}
