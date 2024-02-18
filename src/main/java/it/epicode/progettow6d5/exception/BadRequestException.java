package it.epicode.progettow6d5.exception;

import lombok.Getter;


@Getter
public class BadRequestException extends RuntimeException {
    private ErrorResponse errorResponse;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

}
