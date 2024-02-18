package it.epicode.progettow6d5.service;

import it.epicode.progettow6d5.exception.NotFoundException;
import it.epicode.progettow6d5.model.Dipendente;
import it.epicode.progettow6d5.model.DipendenteRequest;
import it.epicode.progettow6d5.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public DipendenteService(DipendenteRepository dipendenteRepository, JavaMailSender javaMailSender) {
        this.dipendenteRepository = dipendenteRepository;
        this.javaMailSender = javaMailSender;
    }

    public Page<Dipendente> getAll(Pageable pageable) {
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente getById(int id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dipendente con id = " + id + " non trovato"));
    }

    public Dipendente save(DipendenteRequest dipendenteRequest) {
        Dipendente dipendente = convertToEntity(dipendenteRequest);
        sendEmail(dipendente.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    public Dipendente update(int id, DipendenteRequest dipendenteRequest) {
        Dipendente existingDipendente = getById(id);
        Dipendente updatedDipendente = convertToEntity(dipendenteRequest);
        updatedDipendente.setId(existingDipendente.getId());
        return dipendenteRepository.save(updatedDipendente);
    }

    public void delete(int id) {
        Dipendente dipendente = getById(id);
        dipendenteRepository.delete(dipendente);
    }

    public Dipendente updateUrlFoto(int id, String url) {
        Dipendente dipendente = getById(id);
        dipendente.setUrlFoto(url);
        return dipendenteRepository.save(dipendente);
    }

    private Dipendente convertToEntity(DipendenteRequest dipendenteRequest) {
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(dipendenteRequest.getNome());
        dipendente.setEmail(dipendenteRequest.getEmail());
        dipendente.setCognome(dipendenteRequest.getCognome());
        dipendente.setUsername(dipendenteRequest.getUsername());
        return dipendente;
    }

    private void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione nuovo dipendente");
        message.setText("Registrazione del nuovo dipendente effettuata con successo");
        javaMailSender.send(message);
    }
}
