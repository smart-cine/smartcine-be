package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.cinemamanagement.common.BankType;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AddBusinessBankRequest {
    private Map<String, Object> data;
    private BankType type;
    @JsonProperty("cinema_id")
    private UUID cinemaId;
}
