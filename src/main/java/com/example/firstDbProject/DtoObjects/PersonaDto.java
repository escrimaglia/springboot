package com.example.firstDbProject.DtoObjects;

import com.example.firstDbProject.Models.Auto;
import lombok.Data;

import java.util.*;

@Data
public class PersonaDto {
    private Integer dni;
    private String nombre;
    private String apellido;
    private String ciudad;
    private Integer edad;
    private List<Auto> autos;

    public PersonaDto() {
        autos = new ArrayList<>();
    }

    public PersonaDto(Integer dni, String name, String lastname, String city, Integer age) {
        this.dni = dni;
        this.nombre =  name;
        this.apellido = lastname;
        this.ciudad = city;
        this.edad = age;
    }
}

