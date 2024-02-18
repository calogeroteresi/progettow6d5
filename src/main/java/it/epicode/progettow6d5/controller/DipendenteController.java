package it.epicode.progettow6d5.controller;
import com.cloudinary.Cloudinary;
import it.epicode.progettow6d5.exception.BadRequestException;
import it.epicode.progettow6d5.exception.ErrorResponse;
import it.epicode.progettow6d5.model.CustomResponse;
import it.epicode.progettow6d5.model.DipendenteRequest;
import it.epicode.progettow6d5.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/dipendenti")
    public CustomResponse getAll(Pageable pageable){
        return new CustomResponse(HttpStatus.OK.toString(), dipendenteService.getAll(pageable));
    }

    @GetMapping("/dipendenti/{id}")
    public CustomResponse getById(@PathVariable int id){
        return new CustomResponse(HttpStatus.OK.toString(), dipendenteService.getById(id));
    }

    @PostMapping("/dipendenti")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse save(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", bindingResult.getAllErrors());
            throw new BadRequestException(errorResponse);
        }
        return new CustomResponse(HttpStatus.CREATED.toString(), dipendenteService.save(dipendenteRequest));
    }

    @PatchMapping("/dipendenti/{id}/upload")
    public CustomResponse uploadFotoProfilo(@PathVariable int id, @RequestParam("upload") MultipartFile file) {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        if (!Arrays.asList("image/jpeg", "image/png").contains(file.getContentType())) {
            throw new BadRequestException("Only JPEG and PNG images are supported");
        }

        try {

            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), new HashMap());
            String imageUrl = (String) uploadResult.get("url");


            return new CustomResponse(HttpStatus.OK.toString(), dipendenteService.updateUrlFoto(id, imageUrl));
        } catch (IOException e) {
            throw new ServerErrorException("Failed to upload file", e);
        }
    }


    @PutMapping("/dipendenti/{id}")
    public CustomResponse update(@PathVariable int id, @RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().toString());
        return new CustomResponse(HttpStatus.OK.toString(), dipendenteService.update(id, dipendenteRequest));
    }

    @DeleteMapping("/dipendenti/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse delete(@PathVariable int id){
        dipendenteService.delete(id);
        return new CustomResponse(HttpStatus.NO_CONTENT.toString(), null);
    }


}