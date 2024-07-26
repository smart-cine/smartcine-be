package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.PaymentDTO;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.model.Payment;
import org.example.cinemamanagement.model.PickSeat;
import org.example.cinemamanagement.payload.request.AddPaymentRequest;
import org.example.cinemamanagement.repository.CinemaRepository;
import org.example.cinemamanagement.repository.PaymentRepository;
import org.example.cinemamanagement.repository.PickSeatRepository;
import org.example.cinemamanagement.repository.UserRepository;
import org.example.cinemamanagement.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private PickSeatRepository pickSeatRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDTO getPayment(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        PaymentDTO paymentDTO = new PaymentDTO();
        modelMapper.map(paymentDTO, Payment.class);
        return paymentDTO;
    }

    @Override
    public String addPayment(AddPaymentRequest req) {
        Account accountTemp = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = userRepository.findById(accountTemp.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Cinema cinema = cinemaRepository.findById(req.getCinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

        req.getPickSeats().forEach(pickseat -> {
            PickSeat pickSeat = pickSeatRepository.findById(pickseat.getId())
                    .orElseThrow(() -> new RuntimeException("PickSeat not found by Id: " + pickseat.getId()));
        });

        if (req.getAmount() == null)
            throw new RuntimeException("Error payment (amount is NULL)");

        Payment payment = new Payment();
        payment.setAccount(account);
//        payment.setCinema(cinema);
//        payment.setAmount(req.getAmount());
        UUID paymentId = paymentRepository.save(payment).getId();
        System.out.print(paymentId);

        return "Successfully";
    }
}
