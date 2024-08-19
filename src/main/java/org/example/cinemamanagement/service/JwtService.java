package org.example.cinemamanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.service.impl.RedisServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JwtService {


    public Claims extractAllClaims(String token);

    //getAuthorities
    public List<String> getAuthorities(String token);

    public <T> T extractClaim(String token, Function<Claims, T> function);

    public String extractUserName(String token);

    public String extractId(String token);

    public String generateToken(UserDetails userDetails);

    public String generateToken(Map<String, Object> extraClaims, Account userDetails);

    public boolean isTokenValid(String token, String userNameGetedFromDb);

    public boolean isTokenExpired(String token);
}
