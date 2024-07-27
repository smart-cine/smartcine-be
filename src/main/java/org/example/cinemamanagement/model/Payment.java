package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.common.Status;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "perform_id")
    private Perform perform;


    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_expire")
    private Date dateExpire;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private Item item;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    protected void onCreate() {   // create Date when dateCreate saved in db first time
        dateCreate = new Date();
    }
}
