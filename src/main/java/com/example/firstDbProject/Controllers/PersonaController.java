package com.example.firstDbProject.Controllers;

import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.DtoObjects.PersonaDto;
import com.example.firstDbProject.Service.PersonaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    public List<PersonaDto> getPersonas() {
        return svcPersona.getPersonas();
    }

    // Listado de una Persona
    @GetMapping("/api/persona/{dni}")
    public Object getPersona(@PathVariable("dni") Integer dni ) {
        try {
            return svcPersona.getPersona(dni);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una Persona con DNI " + dni.toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Agrega una nueva Persona
    @PostMapping("/api/persona")
    public Object addPersona(@RequestBody Persona persona) {
        try {
            return svcPersona.addPersona(persona);
        } catch (ServiceException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no es posible agregar un Persona con DNI: " + persona.getDni().toString()),null, HttpStatus.BAD_REQUEST);
        }
    }

    // Modifica una persona
    @PutMapping("/api/persona/{dni}")
    public Object changePersona(@RequestBody Persona persona, @PathVariable("dni") Integer dni) {
        try {
            return svcPersona.changePersona(persona,dni);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no existe una Persona con DNI: " + dni.toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Elimina una Persona
    @DeleteMapping("/api/persona/{dni}")
    public Object delPersona(@PathVariable("dni") Integer dni) {
        try {
            return svcPersona.delPersona(dni);
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

