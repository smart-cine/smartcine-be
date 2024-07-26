package org.example.cinemamanagement.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CommentResponse {
    private UUID commentId;
    private UUID accountId;

    @JsonProperty("full_name")
    private String fullName;
    private String comment;
}
