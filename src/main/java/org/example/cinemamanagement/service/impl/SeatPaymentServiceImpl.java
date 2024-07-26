//package org.example.cinemamanagement.service.impl;
//
//import org.example.cinemamanagement.model.Payment;
//import org.example.cinemamanagement.model.PickSeat;
//import org.example.cinemamanagement.model.SeatPayment;
//import org.example.cinemamanagement.payload.request.AddOrDeletePickSeatRequest;
//import org.example.cinemamanagement.repository.PaymentRepository;
//import org.example.cinemamanagement.repository.PickSeatRepository;
//import org.example.cinemamanagement.repository.SeatPaymentRepository;
//import org.example.cinemamanagement.service.SeatPaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class SeatPaymentServiceImpl implements SeatPaymentService {
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private PickSeatRepository pickSeatRepository;
//
//    @Autowired
//    private SeatPaymentRepository seatPaymentRepository;
//
//    @Override
//    public String addListSeatOfPayment(UUID paymentId, List<AddOrDeletePickSeatRequest> listSeat) {
//
//        Payment payment = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//
//        listSeat.stream().forEach(seat -> {
//            PickSeat pickSeat = pickSeatRepository.findById(seat.getId())
//                    .orElseThrow(() -> new RuntimeException("Pick seat not found"));
//        });
//
//
//
//        listSeat.stream().forEach(seat -> {
//            SeatPayment seatPayment = new SeatPayment();
//            seatPayment.setPayment(payment);
//            seatPayment.setPickSeatId(seat.getId());
//            seatPaymentRepository.save(seatPayment);
//        });
//
//        return "Successfully";
//    }
//}
