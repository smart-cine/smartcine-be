package org.example.cinemamanagement.dto.payment;

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
public class BusinessBankDTO {
    private UUID id;
    private Map<String, Object> data;
    private BankType type;
    @JsonProperty("provider_id")
    private UUID providerId;
}
