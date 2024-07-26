package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.dto.UserDTO;
import org.example.cinemamanagement.model.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class AccountMapper {
    public static UserDTO toDTO(Account account) {
        TypeMap<Account, UserDTO> typeMap = new ModelMapper().createTypeMap(Account.class, UserDTO.class);
        return typeMap.map(account);
    }
}
