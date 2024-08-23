package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.dto.cinema.CinemaProviderDTO;
import org.example.cinemamanagement.dto.payment.BusinessBankDTO;
import org.example.cinemamanagement.dto.payment.BusinessBankDTOItem;
import org.example.cinemamanagement.model.BusinessBank;
import org.example.cinemamanagement.model.CinemaProvider;
import org.example.cinemamanagement.repository.BusinessBankRepository;
import org.example.cinemamanagement.repository.CinemaProviderRepository;
import org.example.cinemamanagement.service.BusinessBankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessBankServiceImpl implements BusinessBankService {

    private BusinessBankRepository businessBankRepository;
    private CinemaProviderRepository cinemaProviderRepository;

    public BusinessBankServiceImpl(BusinessBankRepository businessBankRepository, CinemaProviderRepository cinemaProviderRepository) {
        this.businessBankRepository = businessBankRepository;
        this.cinemaProviderRepository = cinemaProviderRepository;
    }

    @Override
    public List<BusinessBankDTO> getAllBusinessBanks() {
        return this.businessBankRepository.findAll().stream().map(businessBank -> BusinessBankDTO.builder()
                .id(businessBank.getId())
                .type(businessBank.getType())
                .data(businessBank.getData())
                .providerId(businessBank.getCinemaProvider().getId())
                .build()).toList();
    }
    @Override
    public BusinessBankDTOItem getBusinessBankById(UUID id) {
        BusinessBank bank = this.businessBankRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bank not found")
        );
        return BusinessBankDTOItem.builder()
                .id(bank.getId())
                .type(bank.getType())
                .data(bank.getData())
                .cinemaProviderDTO(CinemaProviderDTO.builder()
                        .id(bank.getCinemaProvider().getId())
                        .name(bank.getCinemaProvider().getName())
                        .logoUrl(bank.getCinemaProvider().getLogoUrl())
                        .backgroundUrl(bank.getCinemaProvider().getBackgroundUrl())
                        .build())
                .build();
    }
    @Override
    @Transactional
    public BusinessBankDTO saveBusinessBank(BusinessBankDTO businessBankDTO) {
        try {
            CinemaProvider cinemaProvider = this.cinemaProviderRepository.findById(businessBankDTO.getProviderId()).orElseThrow(
                    () -> new RuntimeException("Provider not found")
            );

            BusinessBank businessBank = this.businessBankRepository.save(BusinessBank.builder()
                    .type(businessBankDTO.getType())
                    .data(businessBankDTO.getData())
                    .cinemaProvider(cinemaProvider)
                    .build());

            return BusinessBankDTO.builder()
                    .id(businessBank.getId())
                    .type(businessBank.getType())
                    .data(businessBank.getData())
                    .providerId(businessBank.getCinemaProvider().getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e);
        }

    }

    @Override
    public BusinessBankDTO updateBusinessBank(UUID id, BusinessBankDTO businessBankDTO) {
        return null;
    }

    @Override
    public void deleteBusinessBank(UUID id) {
        this.businessBankRepository.deleteById(id);
    }
}
