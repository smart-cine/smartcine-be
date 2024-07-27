package org.example.cinemamanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cinemamanagement.common.CommentType;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    private Film film;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    private String body;
}