package org.example.cinemamanagement.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.cinemamanagement.common.BankType;
import org.example.cinemamanagement.dto.cinema.CinemaProviderDTO;
import org.hibernate.annotations.SecondaryRow;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BusinessBankDTOItem {
    private UUID id;
    private BankType type;
    private Map<String, Object> data;
    @JsonProperty("cinema_provider")
    private CinemaProviderDTO cinemaProviderDTO;
}
