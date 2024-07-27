package org.example.cinemamanagement.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Payment> payments;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "status")
    private String status;
}
