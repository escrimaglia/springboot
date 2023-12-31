package com.example.firstDbProject.Service;

import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.DtoObjects.PersonaDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.Repository.AutoRepository;
import com.example.firstDbProject.Repository.PersonaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repoPersona;

    @Autowired
    private AutoRepository repoAuto;

    // Agrega una Persona
    public MsgDto addPersona(Persona nueva) throws ServiceException {
        Integer dni = nueva.getDni();
        repoPersona.save(nueva);
        return new MsgDto("Persona con DNI " + dni.toString() + " agregada al padrón");
    }

    // Elimina una Persona
    public MsgDto delPersona(Integer dni) throws NoSuchElementException {
        Persona persona = repoPersona.findById(dni).get();
        repoPersona.deleteById(dni);
        List<Auto> autos = repoAuto.findByPropietario(dni);
        for (Auto auto:autos) {
            auto.setPropietario(null);
        }
        repoAuto.saveAll(autos);
        return new MsgDto("Persona con DNI " + dni.toString() + " eliminada del padrón");
    }

    // Modifica una Persona
    public MsgDto changePutPersona(Persona change, Integer dni) {
        Optional<Persona> persona = repoPersona.findById(dni);
        if (persona.isEmpty()) {
            throw new NoSuchElementException("No existe una persona con DNI " + dni.toString());
        }
        repoPersona.save(change);
        return new MsgDto("Persona con DNI " + dni.toString() + " modificada");
    }

    // Modifica algunos atibutos de una Persona
    public MsgDto changePatchPersona(Persona change, Integer dni) {
        Optional<Persona> persona = repoPersona.findById(dni);
        if (persona.isEmpty()) {
            throw new NoSuchElementException("No existe una Persona con DNI " + dni);
        }
        if (change.getName() != null) {
            persona.get().setName(change.getName());
        }
        if (change.getLastName() != null) {
            persona.get().setLastName(change.getLastName());
        }
        if (change.getCity() != null) {
            persona.get().setCity(change.getCity());
        }
        if (change.getAge() != null) {
            persona.get().setAge(change.getAge());
        }
        repoPersona.save(persona.get());
        return new MsgDto("Persona con DNI " + dni.toString() + " modificada");
    }

    // Lista una Persona
    public PersonaDto getPersona(Integer dni) throws NoSuchElementException {
        Persona persona = repoPersona.findById(dni).get();
        PersonaDto msgpersona = new PersonaDto(persona.getDni(), persona.getName(), persona.getLastName(), persona.getCity(), persona.getAge());
        List<Auto> autos = repoAuto.findByPropietario(dni);
        msgpersona.setAutos(autos);
        return msgpersona;
    }

    // Lista todas las Personas
    public List<PersonaDto> getPersonas() {
        List<Persona> personas = repoPersona.findAll();
        List<PersonaDto> personaResponses = new ArrayList<>();
        for (Persona persona: personas) {
            PersonaDto msgpersona = new PersonaDto(persona.getDni(), persona.getName(), persona.getLastName(), persona.getCity(), persona.getAge());
            List<Auto> autos = repoAuto.findByPropietario(msgpersona.getDni());
            msgpersona.setAutos(autos);
            personaResponses.add(msgpersona);
        }
        return personaResponses;
    }
}
