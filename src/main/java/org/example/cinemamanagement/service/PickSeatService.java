package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.PickSeatDTO;
import org.example.cinemamanagement.payload.request.DeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.PickSeatRequest;
import org.example.cinemamanagement.payload.response.PickSeatResponse;

import java.util.List;
import java.util.UUID;

public interface PickSeatService {
    List<PickSeatResponse> getAllSeatsPickedOfPerform(UUID performID);

    List<PickSeatDTO> getAllPickSeatsByUser();

    PickSeatDTO getPickSeatById();

    public void addPickSeat(PickSeatRequest pickSeatRequests);


    public void deletePickSeat(PickSeatRequest deletePickSeatRequest);

}
