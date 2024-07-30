package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.PickSeatDTO;
import org.example.cinemamanagement.mapper.PickSeatMapper;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.model.CinemaLayoutSeat;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.model.PickSeat;
import org.example.cinemamanagement.payload.request.DeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.PickSeatRequest;
import org.example.cinemamanagement.payload.response.PickSeatResponse;
import org.example.cinemamanagement.repository.CinemaLayoutSeatRepository;
import org.example.cinemamanagement.repository.PerformRepository;
import org.example.cinemamanagement.repository.PickSeatRepository;
import org.example.cinemamanagement.repository.UserRepository;
import org.example.cinemamanagement.service.PickSeatService;
import org.example.cinemamanagement.service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public void addPickSeat(PickSeatRequest pickSeatRequest) {
        Perform perform = performRepository.findById(pickSeatRequest.getPerformID()).orElseThrow(
                () -> new RuntimeException("Perform not found")
        );

        Account account = (Account) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();


        if (pickSeatRepository.findByLayoutSeatIdAndPerformId(pickSeatRequest.getLayoutSeatID(), pickSeatRequest.getPerformID()).isPresent()) {
            throw new RuntimeException("Seat already picked");
        }

        PickSeat pickSeat = PickSeat.builder()
                .perform(perform)
                .account(userRepository.findById(account.getId()).get())
                .layoutSeat(cinemaLayoutSeatRepository.findById(pickSeatRequest.getLayoutSeatID()).orElseThrow(
                        () -> new RuntimeException("Seat not found")))
                .build();

        pickSeatRepository.save(pickSeat);
        socketIOService.emit("post-pickseat", pickSeatRequest);
    }


    @Override
    public List<PickSeatResponse> getAllSeatsPickedOfPerform(UUID performID) {
        Perform perform = performRepository.findById(performID).orElseThrow(
                () -> new RuntimeException("Perform not found")
        );

        return pickSeatRepository.findByPerformId(performID).stream()
                .map(PickSeatMapper::toResponse)
                .toList();
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
    public void deletePickSeat(PickSeatRequest deletePickSeatRequest) {

        Account account = (Account) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        PickSeat pickSeat = pickSeatRepository.findByLayoutSeatIdAndPerformId(deletePickSeatRequest.getPerformID(), deletePickSeatRequest.getLayoutSeatID() ).orElseThrow(
                () -> new RuntimeException("Seat not found"));

        if (!pickSeat.getAccount().getId().equals(account.getId())) {
            throw new RuntimeException("You can only delete your own picked seat");
        }

        pickSeatRepository.deleteByPerformIdAndSeatId(deletePickSeatRequest.getPerformID(), deletePickSeatRequest.getLayoutSeatID());
        socketIOService.emit("delete-pickseat", deletePickSeatRequest);
        return;
    }

}
