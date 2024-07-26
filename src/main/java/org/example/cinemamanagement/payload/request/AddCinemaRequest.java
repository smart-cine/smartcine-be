package org.example.cinemamanagement.payload.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AddCinemaRequest {
    private String address;
    private String name;
    @JsonProperty("provider_id")
    private UUID providerId;
    @JsonProperty("manager_id")
    private UUID managerId;
}
