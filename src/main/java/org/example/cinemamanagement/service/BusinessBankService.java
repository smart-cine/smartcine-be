package org.example.cinemamanagement.service;

import org.example.cinemamanagement.dto.payment.BusinessBankDTO;
import org.example.cinemamanagement.dto.payment.BusinessBankDTOItem;
import org.example.cinemamanagement.payload.request.AddBusinessBankRequest;

import java.util.List;
import java.util.UUID;

public interface BusinessBankService {
    public List<BusinessBankDTO> getAllBusinessBanks();
    public BusinessBankDTOItem getBusinessBankById(UUID id);
    public BusinessBankDTO saveBusinessBank(AddBusinessBankRequest addBusinessBankRequest);
    public BusinessBankDTO updateBusinessBank(UUID id, BusinessBankDTO businessBankDTO);
    public void deleteBusinessBank(UUID id);
}
