package org.example.cinemamanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.cinemamanagement.common.Role;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.example.cinemamanagement.service.JwtService;
import org.example.cinemamanagement.service.impl.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // return 404 status when direct to strange endpoint
        if (request.getRequestURI().equals("/api/v1/payment/ipn") && request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (((authorizationHeader == null) || !authorizationHeader.startsWith("Bearer ")) &&
                (!request.getRequestURI().contains("/api/v1/account"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"JWT Token is missing\"}");
            return;
        } else if (request.getRequestURI().contains("/api/v1/account") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            jwt = authorizationHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                String tempName = userService.checkExistingOfUserInDB(userEmail);

                if(tempName == null) {
                    throw new Exception("User not found");
                }

                UserDetails userDetail = Account.builder()
                        .id(UUID.fromString(jwtService.extractId(jwt)))
                        .email(tempName)
                        .name(tempName)
                        .role(Role.valueOf(jwtService.getAuthorities(jwt).get(0)))
                        .build();


                if (jwtService.isTokenValid(jwt, userDetail.getUsername())) {

                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            // how to throw the response class here
            DataResponse dataResponse = DataResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .build();


            try {
                String jsonResponse = new ObjectMapper().writeValueAsString(dataResponse);
                response.getWriter().write(jsonResponse);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}