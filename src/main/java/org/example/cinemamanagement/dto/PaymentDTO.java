package org.example.cinemamanagement.dto;

import lombok.Data;
import org.example.cinemamanagement.dto.cinema.CinemaDTO;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private UserDTO userDTO;
    private CinemaDTO cinemaDTO;
    private Date dateCreate;
    private Long amount;
}
