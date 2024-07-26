package org.example.cinemamanagement.payload.request;


import lombok.Data;

import java.util.UUID;

@Data
public class AddCinemaRequest {
    private String address;
    private String name;

    private UUID providerId;
}
