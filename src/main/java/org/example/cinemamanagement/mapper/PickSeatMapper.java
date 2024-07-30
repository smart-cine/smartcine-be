package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.PickSeatDTO;
import org.example.cinemamanagement.model.PickSeat;
import org.example.cinemamanagement.payload.response.PickSeatResponse;

public class PickSeatMapper {
    public static PickSeatDTO toDTO(PickSeat pickSeat) {
        return null;
    }
    public static PickSeatResponse toResponse(PickSeat pickSeat) {
        return PickSeatResponse.builder()
                .createAt(pickSeat.getCreateAt())
                .seatID(pickSeat.getLayoutSeat().getId())
                .code(pickSeat.getCode())
                .accountID(pickSeat.getAccount().getId())
                .id(pickSeat.getId())
                .build();
    }
}
