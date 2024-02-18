package it.epicode.progettow6d5.controller;


import it.epicode.progettow6d5.exception.BadRequestException;
import it.epicode.progettow6d5.model.CustomResponse;
import it.epicode.progettow6d5.model.DispositivoRequest;
import it.epicode.progettow6d5.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;



@RestController
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("/dispositivi")
    public CustomResponse getAll(Pageable pageable){
        return new CustomResponse(HttpStatus.OK.toString(), dispositivoService.getAll(pageable));
    }

    @GetMapping("/dispositivi/{id}")
    public CustomResponse getById(@PathVariable int id){
        return new CustomResponse(HttpStatus.OK.toString(), dispositivoService.getById(id));
    }

    @PostMapping("/dispositivi")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse save(@RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().toString());
        return new CustomResponse(HttpStatus.CREATED.toString(), dispositivoService.save(dispositivoRequest));
    }

    @PutMapping("/dispositivi/{id}")
    public CustomResponse update(@PathVariable int id, @RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().toString());
        return new CustomResponse(HttpStatus.OK.toString(), dispositivoService.update(id, dispositivoRequest));
    }

    @DeleteMapping("/dispositivi/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse delete(@PathVariable int id){
        dispositivoService.delete(id);
        return new CustomResponse(HttpStatus.NO_CONTENT.toString(), null);
    }
}
