package it.epicode.progettow6d5.service;

import it.epicode.progettow6d5.exception.NotFoundException;
import it.epicode.progettow6d5.exception.WrongMatchException;
import it.epicode.progettow6d5.model.Dipendente;
import it.epicode.progettow6d5.model.Dispositivo;
import it.epicode.progettow6d5.model.DispositivoRequest;
import it.epicode.progettow6d5.model.Stato;
import it.epicode.progettow6d5.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DipendenteService dipendenteService;

    public Page<Dispositivo> getAll(Pageable pageable){
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo getById(int id){
        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo con id = " + id + " non trovato"));
    }

    public Dispositivo save(DispositivoRequest dispositivoRequest){
        checkCombinations(dispositivoRequest);
        Dipendente dipendente = getDipendenteIfExists(dispositivoRequest.getIdDipendente());
        Dispositivo dispositivo = mapDtoToEntity(dispositivoRequest, dipendente);
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo update(int id, DispositivoRequest dispositivoRequest){
        checkCombinations(dispositivoRequest);
        Dipendente dipendente = getDipendenteIfExists(dispositivoRequest.getIdDipendente());
        Dispositivo dispositivo = getById(id);
        mapDtoToEntity(dispositivoRequest, dipendente, dispositivo);
        return dispositivoRepository.save(dispositivo);
    }

    public void delete(int id){
        Dispositivo dispositivo = getById(id);
        dispositivoRepository.delete(dispositivo);
    }

    private Dipendente getDipendenteIfExists(Integer idDipendente){
        return idDipendente != null ? dipendenteService.getById(idDipendente) : null;
    }

    private Dispositivo mapDtoToEntity(DispositivoRequest dispositivoRequest , Dipendente dipendente){
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setStato(dispositivoRequest.getStato());
        dispositivo.setTipoDispositivo(dispositivoRequest.getTipoDispositivo());
        dispositivo.setDipendente(dipendente);
        return dispositivo;
    }

    private void mapDtoToEntity(DispositivoRequest dispositivoRequest, Dipendente dipendente, Dispositivo dispositivo){
        dispositivo.setStato(dispositivoRequest.getStato());
        dispositivo.setTipoDispositivo(dispositivoRequest.getTipoDispositivo());
        dispositivo.setDipendente(dipendente);
    }

    private void checkCombinations(DispositivoRequest dispositivoRequest){
        Stato stato = dispositivoRequest.getStato();
        Integer idDipendente = dispositivoRequest.getIdDipendente();

        if (stato == Stato.ASSEGNATO && idDipendente == null) {
            throw new WrongMatchException("Il dispositivo non può essere assegnato senza un dipendente associato.");
        }

        if (stato != Stato.ASSEGNATO && idDipendente != null) {
            throw new WrongMatchException("Il dispositivo non può essere " + stato.name().toLowerCase() + " e avere un dipendente assegnato.");
        }
    }
}
