package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.common.BusinessRole;
import org.example.cinemamanagement.common.Role;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_business_ownership")
public class BusinessOwnership {
    @Id
    @Column(name = "owner_id")
    private UUID ownerId;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private BusinessAccount businessAccount;

    @JoinColumn(name = "item_id")
    private UUID itemId;

    @Enumerated(EnumType.STRING)
    private BusinessRole role;
}
