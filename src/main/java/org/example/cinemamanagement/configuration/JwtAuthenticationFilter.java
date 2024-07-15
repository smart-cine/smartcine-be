package org.example.cinemamanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // return 404 status when direct to strange endpoint

        if (((authorizationHeader == null) || !authorizationHeader.startsWith("Bearer ")) &&
                (!request.getRequestURI().contains("/api/v1/user"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"JWT Token is missing\"}");
            return;
        }
        else if(request.getRequestURI().contains("/api/v1/user") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwt = authorizationHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println(jwt);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }

        catch ( Exception e) {
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