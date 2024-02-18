package it.epicode.progettow6d5.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private LocalDateTime dataError;
    private List<ObjectError> errors;

    public ErrorResponse(HttpStatus httpStatus, String validationError, List<ObjectError> allErrors) {
    }

    public ErrorResponse(String message) {
    }
}
