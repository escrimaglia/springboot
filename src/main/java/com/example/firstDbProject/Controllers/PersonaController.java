package com.example.firstDbProject.Controllers;

import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.DtoObjects.PersonaDto;
import com.example.firstDbProject.Service.PersonaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class PersonaController
{
    @Autowired
    private PersonaService svcPersona;

    // Status del Servicio
    @GetMapping("/api/status")
    public String isALive() {
        return "Api is alive";
    }

    // Listado de Personas
    @GetMapping("/api/personas")
    public ResponseEntity<List<PersonaDto>> getPersonas() {
        List<PersonaDto> personas = svcPersona.getPersonas();
        return ResponseEntity.ok(personas);
    }

    // Listado de una Persona
    @GetMapping("/api/persona/{dni}")
    public ResponseEntity getPersona(@PathVariable("dni") Integer dni ) {
        try {
            PersonaDto persona = svcPersona.getPersona(dni);
            return ResponseEntity.ok(persona);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una Persona con DNI " + dni.toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Agrega una nueva Persona
    @PostMapping("/api/persona")
    public ResponseEntity<MsgDto> addPersona(@RequestBody Persona persona) {
        try {
            MsgDto postResult = svcPersona.addPersona(persona);
            return ResponseEntity.ok(postResult);
        } catch (ServiceException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no es posible agregar un Persona con DNI: " + persona.getDni().toString()),null, HttpStatus.BAD_REQUEST);
        }
    }

    // Modifica una persona
    @PutMapping("/api/persona/{dni}")
    public ResponseEntity<MsgDto> changePutPersona(@RequestBody Persona persona, @PathVariable("dni") Integer dni) {
        try {
            MsgDto putResult = svcPersona.changePutPersona(persona,dni);
            return ResponseEntity.ok(putResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una Persona con DNI: " + dni.toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Modifica algunos aributos de una Prsona
    @PatchMapping("api/persona/{dni}")
    public ResponseEntity<MsgDto> changePatchPersona(@RequestBody Persona persona, @PathVariable("dni") Integer dni) {
        try {
            MsgDto patchResult = svcPersona.changePatchPersona(persona,dni);
            return ResponseEntity.ok(patchResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una Persona con DNI " +dni.toString()), null, HttpStatus.NOT_FOUND);
        }
    }

    // Elimina una Persona
    @DeleteMapping("/api/persona/{dni}")
    public ResponseEntity<MsgDto> delPersona(@PathVariable("dni") Integer dni) {
        try {
            MsgDto delResult = svcPersona.delPersona(dni);
            return ResponseEntity.ok(delResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una persona con DNI: " + dni.toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Handling error Json body incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MsgDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new MsgDto("Invalid Request Body"));
    }
}

