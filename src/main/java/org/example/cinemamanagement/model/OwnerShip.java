package org.example.cinemamanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "_ownership")
public class OwnerShip {

    @Id
    @Column(name = "owner_id")
    private UUID ownerId;

    @Id
    @Column(name = "item_id")
    private UUID itemId;
}
