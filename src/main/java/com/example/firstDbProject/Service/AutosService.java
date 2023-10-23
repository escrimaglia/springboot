package com.example.firstDbProject.Service;

import com.example.firstDbProject.DtoObjects.AutoDto;
import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Models.Persona;
import com.example.firstDbProject.Repository.AutosRepository;
import com.example.firstDbProject.Repository.PersonasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AutosService {
    @Autowired
    private AutosRepository repoAuto;
    @Autowired
    private PersonasRepository repoPersona;

    // Agrega un Auto
    public MsgDto addAuto(Auto nuevo) throws Exception {
        repoAuto.save(nuevo);
        return new MsgDto("Auto Modelo: " + nuevo.getModelo().toString() + " agregado al padrón de autos");
    }

    // Elimina un Auto
    public MsgDto delAuto(Integer modelo) {
        Optional<Auto> auto = repoAuto.findById(modelo);
        if (auto.isEmpty()) {
            throw new NoSuchElementException("No existe Auto modelo " + modelo);
        }
        repoAuto.deleteById(modelo);
        return new MsgDto("Auto Modelo: " + modelo.toString() + " eliminado del padrón");
    }

    // Modifica todos los atributos de un Auto
    public MsgDto changePutAuto(Auto change, Integer modelo) throws NoSuchElementException {
        Optional<Auto> auto = repoAuto.findById(modelo);
        if (auto.isEmpty()) {
            throw new NoSuchElementException("No existe Auto modelo " + modelo);
        }
        if (change.getPropietario() != null) {
            Persona persona = repoPersona.findByDni(change.getPropietario());
            if (persona == null) {
                throw new NoSuchElementException("No existe propietario DNI " + change.getPropietario());
            }

        }
        change.setModelo(modelo);
        repoAuto.save(change);
        return new MsgDto("Auto Modelo: " + modelo.toString() + " modificado");
    }

    // Modifica algunos atributos de un Auto
    public MsgDto changePatchAuto(Auto change, Integer modelo) {

        Optional<Auto> auto = repoAuto.findById(modelo);
        if (auto.isEmpty()) {
            throw new NoSuchElementException("No existe Auto modelo " + modelo);
        }
        if (change.getMarca() != null) {
            auto.get().setMarca(change.getMarca());
        }
        if (change.getColor() != null) {
            auto.get().setColor(change.getColor());
        }
        if (change.getAnio() != null) {
            auto.get().setAnio(change.getAnio());
        }
        if (change.getPropietario() != null) {
            Persona persona = repoPersona.findByDni(change.getPropietario());
            if (persona == null) {
                throw new NoSuchElementException("No existe propietario DNI " + change.getPropietario());
            }
            auto.get().setPropietario(change.getPropietario());
        }
        repoAuto.save(auto.get());
        return new MsgDto("Auto Modelo: " + modelo.toString() + " modificado");
    }

    // Lista un Auto
    public AutoDto getAuto(Integer modelo) {
        Optional<Auto> auto = repoAuto.findById(modelo);
        if (auto.isEmpty()) {
            throw new NoSuchElementException("No existe Auto modelo " + modelo);
        }
        Integer dni = repoAuto.findById(modelo).get().getPropietario();
        AutoDto msgauto;
        if (dni != null) {
            Persona persona = repoPersona.findById(dni).get();
             msgauto = new AutoDto(auto.get().getModelo(),auto.get().getMarca(),auto.get().getColor(),auto.get().getAnio(),persona);
        } else {
             msgauto = new AutoDto(auto.get().getModelo(),auto.get().getMarca(),auto.get().getColor(),auto.get().getAnio(),null);
        }
        return msgauto;
    }

    // Lista todos los Autos
    public List<AutoDto> getAutos() {
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
