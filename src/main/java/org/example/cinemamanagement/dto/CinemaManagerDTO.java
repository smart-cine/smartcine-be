package org.example.cinemamanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.model.Cinema;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaManagerDTO {
    @JsonProperty("cinema_manager")
    private Account account;
    @JsonProperty("cinemas")
    private List<Cinema> cinemas;
}
