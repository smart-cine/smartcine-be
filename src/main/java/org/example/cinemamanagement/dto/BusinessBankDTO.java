package org.example.cinemamanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class BusinessBankDTO {
    private UUID id;
    private String data;
}
