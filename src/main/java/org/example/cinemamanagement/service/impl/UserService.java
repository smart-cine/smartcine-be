package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    public String checkExistingOfUserInDB(String email) {
        if (RedisServiceImpl.exists(email)) {
            return email;
        }

        if (accountRepository.existsByEmail(email)) {
            RedisServiceImpl.getJedisResource().set(email, email);
            RedisServiceImpl.expire(email, Duration.ofHours(2).getSeconds());
            return email;
        }

        return null;
    }
}
