package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String checkExistingOfUserInDB(String email) {
        if (RedisServiceImpl.getJedisResource().get(email) != null) {
            return email;
        }

        if (userRepository.existsByEmail(email)) {
            RedisServiceImpl.getJedisResource().set(email, email);
            RedisServiceImpl.expire(email, Duration.ofDays(2).getSeconds());
            return email;
        }

        return null;
    }
}
