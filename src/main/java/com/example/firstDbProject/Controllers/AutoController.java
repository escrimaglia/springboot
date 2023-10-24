package com.example.firstDbProject.Controllers;

import com.example.firstDbProject.DtoObjects.AutoDto;
import com.example.firstDbProject.DtoObjects.MsgDto;
import com.example.firstDbProject.Models.Auto;
import com.example.firstDbProject.Service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AutoController {

    @Autowired
    private AutoService svcAuto;

    // Listado de Autos
    @GetMapping("/api/autos")
    public ResponseEntity<List<AutoDto>> getAutos() {
        List<AutoDto> autos =  svcAuto.getAutos();
        return ResponseEntity.ok(autos);
    }

    // Listado un Auto
    @GetMapping("/api/auto/{modelo}")
    public ResponseEntity getAuto(@PathVariable("modelo") Integer modelo) {
        try {
            AutoDto auto = svcAuto.getAuto(modelo);
            return ResponseEntity.ok(auto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Agrega un nuevo Auto
    @PostMapping("/api/auto")
    public ResponseEntity addAuto(@RequestBody Auto auto) {
        try {
            MsgDto addResult = svcAuto.addAuto(auto);
            return ResponseEntity.status(HttpStatus.CREATED).body(addResult);
        } catch (Exception e) {
            return new ResponseEntity<MsgDto>(new MsgDto("no es posible agregar un Auto modelo " + auto.getModelo().toString()),null, HttpStatus.BAD_REQUEST);
        }
    }

    // Modifica un Auto
    @PutMapping("/api/auto/{modelo}")
    public ResponseEntity<MsgDto> changeWholeAuto(@RequestBody Auto auto, @PathVariable("modelo") Integer modelo) {
        try {
            MsgDto putResult = svcAuto.changePutAuto(auto,modelo);
            return ResponseEntity.ok(putResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Modifica algunos atributos de un Auto
    @PatchMapping("/api/auto/{modelo}")
    public ResponseEntity<MsgDto> changeAuto(@RequestBody Auto auto, @PathVariable("modelo") Integer modelo) {
        try {
            MsgDto pathcResult = svcAuto.changePatchAuto(auto,modelo);
            return ResponseEntity.ok(pathcResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null,HttpStatus.NOT_FOUND);
        }
    }

    // Elimina un Auto
    @DeleteMapping("/api/auto/{modelo}")
    public ResponseEntity<MsgDto> delAuto(@PathVariable("modelo") Integer modelo) {
        try {
            MsgDto delResult = svcAuto.delAuto(modelo);
            return ResponseEntity.ok(delResult);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MsgDto>(new MsgDto(e.getMessage().toString()),null, HttpStatus.NOT_FOUND);
        }
    }

    // Handling error Json body incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MsgDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new MsgDto("Invalid Request Body"));
    }
}
