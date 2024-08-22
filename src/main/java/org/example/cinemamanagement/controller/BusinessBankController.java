package org.example.cinemamanagement.controller;

import org.example.cinemamanagement.dto.BusinessBankDTO;
import org.example.cinemamanagement.model.BusinessBank;
import org.example.cinemamanagement.repository.BusinessBankRepository;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/business-bank")
public class BusinessBankController {
    @Autowired
    private BusinessBankRepository businessBankRepository;

    @GetMapping
    public Object getBusinessBank() {
        List<BusinessBank> bi = businessBankRepository.findAll();

        return bi.stream().map(businessBank -> {
            return BusinessBankDTO.builder()
                    .data(businessBank.getData())
                    .id(businessBank.getId())
            .build();
        });
    }
}
