package org.example.cinemamanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.cinemamanagement.common.BusinessRole;

import java.util.UUID;

@Getter
@Setter
@Builder
public class BusinessOwnerShipDTO {
    @JsonProperty("owner_id")
    private UUID ownerID;
    @JsonProperty("item_id")
    private UUID item_id;
    private BusinessRole role;
}
