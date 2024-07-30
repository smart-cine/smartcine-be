package org.example.cinemamanagement.exception;

import org.example.cinemamanagement.common.ErrorKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(RuntimeException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .success(false)
                .statusCode(ErrorKey.ErrorInternal)
                .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                .message(ex.getLocalizedMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({AuthenticationCredentialsNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationCredentialsNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .success(false)
                .statusCode(ErrorKey.ErrorWrongCreds)
                .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                .message(ex.getLocalizedMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .success(false)
                .statusCode(ErrorKey.ErrorInternal)
                .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                .message(ex.getLocalizedMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .success(false)
                .statusCode(ErrorKey.ErrorInternal)
                .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                .message(ex.getLocalizedMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
