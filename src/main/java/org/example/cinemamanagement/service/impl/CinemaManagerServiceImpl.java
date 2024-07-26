//package org.example.cinemamanagement.service.impl;
//
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.example.cinemamanagement.common.Role;
//import org.example.cinemamanagement.dto.CinemaManagerDTO;
//import org.example.cinemamanagement.model.Cinema;
//import org.example.cinemamanagement.model.User;
//import org.example.cinemamanagement.repository.BusinessRepository;
//import org.example.cinemamanagement.repository.CinemaRepository;
//import org.example.cinemamanagement.repository.UserRepository;
//import org.example.cinemamanagement.service.CinemaManagerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//@NoArgsConstructor
//public class CinemaManagerServiceImpl implements CinemaManagerService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    CinemaRepository cinemaRepository;
//
//    @Autowired
//    private BusinessRepository businessRepository;
//
//    @Override
//    @Transactional
//    public CinemaManagerDTO addCinemaManager(String emailUser, UUID idCinema) {
//        User user = userRepository.findUserByEmail(emailUser).get();
//        Cinema cinema = cinemaRepository.findById(idCinema).get();
//        if (cinema.getCinemaManagers().contains(user)) {
//            return null;
//        }
//        user.setRole(Role.MANAGER_ADMIN);
//        cinema.addUser(user);
//        return CinemaManagerDTO.builder().cinemas(user.getCinemas()).user(user).build();
//
//    }
//
//    @Override
//    public void updateCinemaManager(String emailUser, UUID idCinema) {
//
//    }
//
//    @Override
//    public void getCinemaManager(String emailUser, UUID idCinema) {
//
//    }
//
//    @Override
//    public List<CinemaManagerDTO> getAllCinemaManagerFromCinema(UUID idCinema) {
//        return cinemaRepository.findById(idCinema).get()
//                .getCinemaManagers()
//                .stream().map(
//                        user -> {
//                            return CinemaManagerDTO.builder().user(user)
//                                    .cinemas(user.getCinemas())
//                                    .build();
//                        }
//                ).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Object[]> getTotalAmountOfCinemaInMonth() {
//        User userTemp = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findById(userTemp.getId()).orElseThrow(
//                () -> new RuntimeException("User not found")
//        );
//
//        LocalDate today = LocalDate.now();
//        LocalDate thirtyDaysAgo = today.minus(30, ChronoUnit.DAYS);
//
//        return businessRepository.getTotalAmountOfCinemaInMonth(userTemp.getId(), thirtyDaysAgo, today);
//    }
//
//}
//
