package it.epicode.progettow6d5.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DipendenteRequest {
    @NotNull(message = "Username obbligatorio")
    @NotEmpty(message = "Username non vuoto")
    private String username;
    @Email(message = "Formato email non valido")
    private String email;
    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "Nome non vuoto")
    private String nome;
    @NotNull(message = "Cognome obbligatorio")
    @NotEmpty(message = "Cognome non vuoto")
    private String cognome;

}
