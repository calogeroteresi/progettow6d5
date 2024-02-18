package it.epicode.progettow6d5.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomResponse {
    private String message;
    private LocalDateTime dateResponse;
    private Object response;

    public CustomResponse(String message, Object response) {
        this.message = message;
        this.response = response;
        dateResponse = LocalDateTime.now();
    }
}
