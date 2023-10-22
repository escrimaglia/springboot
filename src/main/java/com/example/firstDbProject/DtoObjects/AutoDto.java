package com.example.firstDbProject.DtoObjects;
import com.example.firstDbProject.Models.Persona;
import lombok.Data;

@Data
public class AutoDto {
    private Integer modelo;
    private String marca;
    private String color;
    private Integer anio;
    private Persona propietario;

    public AutoDto(Integer modelo, String marca, String color, Integer anio, Persona propietario) {
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
        this.anio = anio;
        this.propietario = propietario;
    }
}

