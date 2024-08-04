package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.common.SeatStatus;
import org.example.cinemamanagement.dto.PickSeatDTO;
import org.example.cinemamanagement.mapper.PickSeatMapper;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.model.CinemaLayoutSeat;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.model.PickSeat;
import org.example.cinemamanagement.payload.request.AddOrDeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.DeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.PickSeatRequest;
import org.example.cinemamanagement.payload.response.PickSeatResponse;
import org.example.cinemamanagement.repository.CinemaLayoutSeatRepository;
import org.example.cinemamanagement.repository.PerformRepository;
import org.example.cinemamanagement.repository.PickSeatRepository;
import org.example.cinemamanagement.repository.UserRepository;
import org.example.cinemamanagement.service.PickSeatService;
import org.example.cinemamanagement.service.RedisService;
import org.example.cinemamanagement.service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PickSeatServiceImpl implements PickSeatService {
    PickSeatRepository pickSeatRepository;
    PerformRepository performRepository;
    CinemaLayoutSeatRepository cinemaLayoutSeatRepository;
    UserRepository userRepository;
    SocketIOService socketIOService;

    @Autowired
    public PickSeatServiceImpl(PickSeatRepository pickSeatRepository, PerformRepository performRepository, UserRepository userRepository,
                               CinemaLayoutSeatRepository cinemaLayoutSeatRepository,
                               SocketIOService socketIOService
    ) {

        this.pickSeatRepository = pickSeatRepository;
        this.performRepository = performRepository;
        this.userRepository = userRepository;
        this.cinemaLayoutSeatRepository = cinemaLayoutSeatRepository;
        this.socketIOService = socketIOService;
    }

    @Override
    @Transactional
    public PickSeatRequest addPickSeat(PickSeatRequest pickSeatRequest) {
        Perform perform = performRepository.findById(pickSeatRequest.getPerformID()).orElseThrow(
                () -> new RuntimeException("Perform not found")
        );

        Account account = (Account) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Set<String> keys = RedisService.keys("pickseat:" + "*:" + account.getId());

        if (!keys.contains("pickseat:" + pickSeatRequest.getPerformID() + ":" + account.getId()) && keys.size() > 0) {
            throw new RuntimeException("You can only pick 1 seats");
        }


        RedisService.sadd("pickseat:" + pickSeatRequest.getPerformID() + ":" + account.getId(), String.valueOf(pickSeatRequest.getLayoutSeatID()));

        return PickSeatRequest.builder()
                .layoutSeatID(pickSeatRequest.getLayoutSeatID())
                .performID(pickSeatRequest.getPerformID())
                .build();
//        socketIOService.emit("post-pickseat", pickSeatRequest);
    }


    @Override
    public List<PickSeatResponse> getAllSeatsPickedOfPerform(UUID performID) {
        Perform perform = performRepository.findById(performID).orElseThrow(
                () -> new RuntimeException("Perform not found")
        );

        List<PickSeatResponse> pickSeatPickSeatResponses = new LinkedList<>();

        RedisService.keys("pickseat:" + performID.toString() + ":*").forEach(value -> {
            pickSeatPickSeatResponses.add(PickSeatResponse.builder()
                    .seatID(UUID.fromString(value.split(":")[2]))
                    .status(SeatStatus.PENDING)
                    .build());
        });

        pickSeatRepository.findByPerformId(performID).forEach(pickSeat -> {
            pickSeatPickSeatResponses.add(PickSeatMapper.toResponse(pickSeat));
        });

        return pickSeatPickSeatResponses;
    }

    @Override
    public List<PickSeatDTO> getAllPickSeatsByUser() {
        Account account = (Account) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return pickSeatRepository.findByAccountId(account.getId()).stream()
                .map(PickSeatMapper::toDTO)
                .toList();
    }

    @Override
    public PickSeatDTO getPickSeatById() {
        return null;
    }

    @Override
    @Transactional
    public PickSeatRequest deletePickSeat(PickSeatRequest deletePickSeatRequest) {

        Account account = (Account) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        RedisService.srem("pickseat:" + deletePickSeatRequest.getPerformID() + ":" + account.getId(), String.valueOf(deletePickSeatRequest.getLayoutSeatID()));

        return deletePickSeatRequest;
    }

}
