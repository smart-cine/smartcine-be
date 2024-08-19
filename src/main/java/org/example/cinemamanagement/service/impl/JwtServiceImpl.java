package org.example.cinemamanagement.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";

    private Key getSigninKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    //getAuthorities
    public List<String> getAuthorities(String token) {
//        return List.of(new SimpleGrantedAuthority(role.name()));
        Claims claims = extractAllClaims(token);
        return claims.get("authorities", List.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> function) {
        Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public String generateToken(UserDetails userDetails) {

        return generateToken(
                Map.of("authorities", userDetails
                        .getAuthorities()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.toList())
                )
                , (Account) userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, Account userDetails) {
        RedisServiceImpl.getJedisResource().set(userDetails.getEmail(), userDetails.getId().toString());
        RedisServiceImpl.expire(userDetails.getEmail(), Duration.ofDays(2).getSeconds());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setId(String.valueOf(userDetails.getId()))
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(30).toMillis()))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String userNameGetedFromDb) {
        final String userName = extractUserName(token);
        return userName.equals(userNameGetedFromDb) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return new Date().after(date);
    }
}
