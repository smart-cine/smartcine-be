//package org.example.cinemamanagement.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.util.UUID;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "seat_payment")
//public class SeatPayment {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
//            CascadeType.PERSIST,
//            CascadeType.REFRESH}
//    )
//    private Payment payment;
//
//    private UUID pickSeatId;
//
//}
