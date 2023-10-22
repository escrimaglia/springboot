package com.example.firstDbProject.Service;

import com.example.firstDbProject.DtoObjects.AutoDto;
import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.Repository.AutosRepository;
import com.example.firstDbProject.Repository.PersonasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AutosService {
    @Autowired
    private AutosRepository repoAuto;
    @Autowired
    private PersonasRepository repoPersona;

    // Agrega un Auto
    public MsgDto addAuto(Auto nuevo)  {
        repoAuto.save(nuevo);
        return new MsgDto("Auto Modelo: " + nuevo.getModelo().toString() + " agregado al padrón de autos");
    }

    // Elimina un Auto
    public MsgDto delAuto(Integer modelo) throws NoSuchElementException {
        Auto auto = repoAuto.findById(modelo).get();
        repoAuto.deleteById(modelo);
        return new MsgDto("Auto Modelo: " + modelo.toString() + " eliminado del padrón");
    }

    // Modifica un Auto
    public MsgDto changeAuto(Auto change, Integer modelo) throws NoSuchElementException {
        Auto auto = repoAuto.findById(modelo).get();
        repoAuto.save(change);
        return new MsgDto("Auto Modelo: " + modelo.toString() + " modificado");
    }

    // Lista un Auto
    public AutoDto getAuto(Integer modelo)  throws NoSuchElementException {
        Auto auto = repoAuto.findById(modelo).get();
        Integer dni = repoAuto.findById(modelo).get().getPropietario();
        AutoDto msgauto;
        if (dni != null) {
            Persona persona = repoPersona.findById(dni).get();
             msgauto = new AutoDto(auto.getModelo(),auto.getMarca(),auto.getColor(),auto.getAnio(),persona);
        } else {
             msgauto = new AutoDto(auto.getModelo(),auto.getMarca(),auto.getColor(),auto.getAnio(),null);
        }
        return msgauto;
    }

    // Lista todos los Autos
    public List<AutoDto> getAutos() throws NoSuchElementException, HttpServerErrorException.InternalServerError {
        List<Auto> autos = repoAuto.findAll();
        List<AutoDto> msgautos = new ArrayList<AutoDto>();
        AutoDto msgauto;
        for (Auto auto: autos) {
            if (auto.getPropietario() != null ) {
                Persona persona = repoPersona.findById(auto.getPropietario()).get();
                 msgauto = new AutoDto(auto.getModelo(), auto.getMarca(), auto.getColor(), auto.getAnio(), persona);
            } else {
                 msgauto = new AutoDto(auto.getModelo(), auto.getMarca(), auto.getColor(), auto.getAnio(), null);
            }
            msgautos.add(msgauto);
        }
        return msgautos;
    }
}
