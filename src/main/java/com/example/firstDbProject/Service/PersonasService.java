package com.example.firstDbProject.Service;

import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.DtoObjects.PersonaDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.Repository.AutosRepository;
import com.example.firstDbProject.Repository.PersonasRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PersonasService {

    @Autowired
    private PersonasRepository  repoPersona;

    @Autowired
    private AutosRepository repoAuto;

    // Agrega una Persona
    public MsgDto addPersona(Persona nueva) throws ServiceException {
        Integer dni = nueva.getDni();
        repoPersona.save(nueva);
        return new MsgDto("Persona DNI: " + dni.toString() + " agregada al padrón");
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
        return new MsgDto("Persona DNI: " + dni.toString() + " eliminada del padrón");
    }

    // Modifica una Persona
    public MsgDto changePersona(Persona change, Integer dni) throws NoSuchElementException {
        Persona persona = repoPersona.findById(dni).get();
        repoPersona.save(change);
        return new MsgDto("Persona DNI: " + dni.toString() + " modificada");
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
