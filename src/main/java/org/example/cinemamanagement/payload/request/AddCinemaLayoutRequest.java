package org.example.cinemamanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.cinemamanagement.common.LayoutType;

@Data
public class AddCinemaLayoutRequest {

    @JsonProperty("manager_id")
    private String managerId;
    private Integer rows;
    private Integer columns;
}
