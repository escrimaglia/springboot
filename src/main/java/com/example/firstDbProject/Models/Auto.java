package com.example.firstDbProject.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "autos")
public class Auto {
    @Id
    @Column(name = "modelo")
    private Integer modelo;
    @Column(name = "marca")
    private String marca;
    @Column(name = "color")
    private String color;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "dni")
    private Integer propietario;

    public Auto() {
    }

    public Auto(Integer modelo, String marca, String color, Integer anio, Integer dni) {
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
        this.anio = anio;
        this.propietario = dni;
    }
}
