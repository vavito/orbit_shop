package com.orbit_shop.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Map;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(
            Exception e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError err = new StandardError(
                URI.create("/errors/internal-server-error"),
                status.getReasonPhrase(),
                status.value(),
                "Unexpected internal error",
                URI.create(request.getRequestURI()),
                Instant.now(),
                Map.of(
                        "path", request.getRequestURI(),
                        "method", request.getMethod()
                )
        );

        return ResponseEntity.status(status).body(err);
    }
}
