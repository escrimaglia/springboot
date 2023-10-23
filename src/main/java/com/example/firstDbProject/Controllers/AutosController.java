package com.example.firstDbProject.Controllers;

import com.example.firstDbProject.DtoObjects.AutoDto;
import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Service.AutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AutosController {

    @Autowired
    private AutosService svcAuto;

    // Listado de Autos
    @GetMapping("/api/autos")
    public List<AutoDto> getAutos() {
        return svcAuto.getAutos();
    }

    // Listado un Auto
    @GetMapping("/api/auto/{modelo}")
    //@ResponseBody
    public Object getAuto(@PathVariable("modelo") Integer modelo) {
        try {
            return svcAuto.getAuto(modelo);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Agrega un nuevo Auto
    @PostMapping("/api/auto")
    public Object addAuto(@RequestBody Auto auto) {
        try {
            return svcAuto.addAuto(auto);
        } catch (Exception e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no es posible agregar un Auto modelo " + auto.getModelo().toString()),null, HttpStatus.BAD_REQUEST);
        }
    }

    // Modifica un Auto
    @PutMapping("/api/auto/{modelo}")
    public Object changeWholeAuto(@RequestBody Auto auto, @PathVariable("modelo") Integer modelo) {
        try {
            return svcAuto.changePutAuto(auto,modelo);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Modifica algunos atributos de un auto
    @PatchMapping("/api/auto/{modelo}")
    public Object changeAuto(@RequestBody Auto auto, @PathVariable("modelo") Integer modelo) {
        try {
            return svcAuto.changePatchAuto(auto,modelo);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null,HttpStatus.NOT_FOUND);
        }
    }

    // Elimina un Auto
    @DeleteMapping("/api/auto/{modelo}")
    public Object delAuto(@PathVariable("modelo") Integer modelo) {
        try {
            return svcAuto.delAuto(modelo);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }
}
