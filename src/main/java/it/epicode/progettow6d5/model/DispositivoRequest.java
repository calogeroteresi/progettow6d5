package it.epicode.progettow6d5.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DispositivoRequest {
    private TipoDispositivo tipoDispositivo;
    private Stato stato;
    @Min(value = 1, message = "L'id dipendente minimo accettato è 1")
    private Integer idDipendente;
}
