package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.PickSeatDTO;
import org.example.cinemamanagement.mapper.PickSeatMapper;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.payload.request.DeletePickSeatRequest;
import org.example.cinemamanagement.payload.request.PickSeatRequest;
import org.example.cinemamanagement.repository.PerformRepository;
import org.example.cinemamanagement.repository.PickSeatRepository;
import org.example.cinemamanagement.repository.UserRepository;
import org.example.cinemamanagement.service.PickSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PickSeatServiceImpl implements PickSeatService {
    PickSeatRepository pickSeatRepository;
    PerformRepository performRepository;

    UserRepository userRepository;

    @Autowired
    public PickSeatServiceImpl(PickSeatRepository pickSeatRepository, PerformRepository performRepository, UserRepository userRepository) {
        this.pickSeatRepository = pickSeatRepository;
        this.performRepository = performRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Object addPickSeat(List<PickSeatRequest> pickSeatRequests, UUID performId) {
//        Perform perform = performRepository.findById(performId).orElseThrow(
//                () -> new RuntimeException("Perform not found")
//        );
//
//        User userTemp = (User) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        User user = userRepository.findById(userTemp.getId()).orElseThrow(
//                () -> new RuntimeException("User not found"));
//
//        pickSeatRequests.forEach(pickSeatRequest -> {
//            if (pickSeatRepository.findByPerformIdAndXAndY(performId, pickSeatRequest.getX(), pickSeatRequest.getY()).isPresent()) {
//                throw new RuntimeException("Seat already picked");
//            }
//
//            if (pickSeatRequest.getX() < 0 || pickSeatRequest.getX() > perform.getCinemaRoom().getCinemaLayout().getXIndex() ||
//                    pickSeatRequest.getY() < 0 || pickSeatRequest.getY() > perform.getCinemaRoom().getCinemaLayout().getYIndex()) {
//                throw new RuntimeException("Seat out of range");
//            }
//
//            PickSeat pickSeat = PickSeat.builder()
//                    .perform(perform)
//                    .user(user)
//                    .x(pickSeatRequest.getX())
//                    .y(pickSeatRequest.getY())
//                    .build();
//            pickSeatRepository.save(pickSeat);
//        });
//
//        return Map.of("seats", pickSeatRequests.stream().map(pickSeatRequest -> {
//            return SocketResponse.builder()
//                    .x(pickSeatRequest.getX())
//                    .y(pickSeatRequest.getY())
//                    .build();
//        }), "performID", performId);

        return null;
    }

    @Override
    public List<PickSeatDTO> getAllSeatsPickedOfPerform(UUID performID) {
        return pickSeatRepository.findByPerformId(performID).stream()
                .map(PickSeatMapper::toDTO).toList();
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
    public Object deletePickSeat(List<DeletePickSeatRequest> deletePickSeatRequests, UUID performID) {
//        deletePickSeatRequests.forEach(deletePickSeatRequest -> {
//            pickSeatRepository.deleteByXAndY(deletePickSeatRequest.getX(),
//                    deletePickSeatRequest.getY(),
//                    performID
//            );
//        });

//        return Map.of("seats", deletePickSeatRequests.stream().map(deletePickSeatRequest -> {
//            return SocketResponse.builder()
//                    .x(deletePickSeatRequest.getX())
//                    .y(deletePickSeatRequest.getY())
//                    .build();
//        }).toList(), "performID", performID);

        return null;
    }

}
